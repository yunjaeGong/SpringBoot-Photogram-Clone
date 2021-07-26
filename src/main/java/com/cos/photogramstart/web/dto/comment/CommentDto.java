package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    @NotBlank // 빈 값, 공백, null
    @Size(min = 1, max = 300)
    private String content;

    @NotNull
    private long imageId;
}
