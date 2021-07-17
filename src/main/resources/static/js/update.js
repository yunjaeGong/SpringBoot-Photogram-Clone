// (1) 회원정보 수정
function update(userId, event) {
    event.preventDefault(); // form tag default 동작 막기
    let data = $("#profileUpdate").serialize();
    console.log(data);

    $.ajax({
        type: "put",
        url: `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded: charset=utf-8",
        dataType: "json"
    }).done(res => {
        console.log("done", res);
        location.href = '/user/${userId}';
    }).fail(error => { // HTTP Status Code 200아닐때
        if (error.responseJSON.data == null) {
            alert(error.responseJSON.message);
        } else {
            alert(JSON.stringify(error.responseJSON.data));
        }
    });
}