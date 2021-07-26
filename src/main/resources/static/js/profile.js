/**
 1. 유저 프로파일 페이지
 (1) 유저 프로파일 페이지 구독하기, 구독취소
 (2) 구독자 정보 모달 보기
 (3) 구독자 정보 모달에서 구독하기, 구독취소
 (4) 유저 프로필 사진 변경
 (5) 사용자 정보 메뉴 열기 닫기
 (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
 (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
 (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) { // obj - 이벤트 정보
    if ($(obj).text() === "구독취소") { // 구독 된 상태
        $.ajax({
            type: "delete",
            url: "/api/unsubscribe/" + toUserId,
            dataType: "json",
        }).done(res => {
            $(obj).text("구독하기");
            $(obj).toggleClass("blue");
        }).fail(res => {
            console.log("구독취소 실패");
        });
    } else { // 구독 안된 상태
        $.ajax({
            type: "post",
            url: "/api/subscribe/" + toUserId,
            dataType: "json",
        }).done(res => {
            $(obj).text("구독취소");
            $(obj).toggleClass("blue");
        }).fail(res => {
            console.log("구독 실패");
        });
    }
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
    $(".modal-subscribe").css("display", "flex");
    $.ajax({
        type: "GET",
        url: `/api/user/${pageUserId}/subscribe`,
        datatype: "json",
    }).done(res => {
        console.log(res);

        res.data.forEach((u) => {
            let item = getSubscribeModalItem(u);
            $("#subscribeModalList").append(item);
        });
    }).fail(error => {
        console.log("구독자 정보를 불러오는데 실패 ", error);
    })
}

function getSubscribeModalItem(u) {
    let item = `<div class="subscribe__item" id="subscribeModalItem-${u.id}">
	<div class="subscribe__img">
		<img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />
	</div>
	<div class="subscribe__text">
		<h2>${u.username}</h2>
	</div>
	<div class="subscribe__btn">`;

    if(!u.equalUserState){ // 동일 유저가 아닐 때만 버튼이 보임
        if(u.subscribeState){ // 구독한 상태
            item += `<button class="cta blue" onclick="toggleSubscribe(${u.id}, this)">구독취소</button>`;
        }else{ // 구독안한 상태
            item += `<button class="cta" onclick="toggleSubscribe(${u.id}, this)">구독하기</button>`;
        }
    }
    item += `	
	</div>
</div>`;

    return item;
}

// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(principalId, pageUserId) {
    if (principalId != pageUserId) {
        return;
    }

    $("#userProfileImageInput").click(); // 강제로 form 클릭

    $("#userProfileImageInput").on("change", (e) => {
        let f = e.target.files[0];

        if (!f.type.match("image.*")) {
            alert("이미지를 등록해야 합니다.");
            return;
        }

        // 서버에 이미지 전송
        let profileImageForm = $("#userProfileImageForm")[0]; // form tag
        console.log(profileImageForm);

        // form 데이터 전송 위해 FormData 객체를 이용, input field 값을 key/value로 담는다.
        let formData = new FormData(profileImageForm);
        $.ajax({
            type: "put",
            url: `/api/user/${principalId}/profileImageUrl`,
            data: formData,
            contentType: false, // 기본 값 x-www-form-urlencoded로 파싱 방지
            processData: false, // contentType을 false로 했을 때 QueryString으로 파싱되는 것을 방지
            datatype: "json",
        }).done(res => {
            // 사진 전송 성공시 이미지 변경
            let reader = new FileReader();
            reader.onload = (e) => {
                $("#userProfileImage").attr("src", e.target.result);
            }
            reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
        }).fail(error => {
            console.log("프로필 사진 변경 실패", error)
        });
    });
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
    $(obj).css("display", "flex");
}

function closePopup(obj) {
    $(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
    $(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
    $(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
    $(".modal-subscribe").css("display", "none");
    location.reload();
}






