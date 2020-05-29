package com.shen.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: shenge
 * @Date: 2020-05-12 09:41
 * <p>
 * 章节
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    private List<VideoVo> videoVos = new ArrayList<>();

}
