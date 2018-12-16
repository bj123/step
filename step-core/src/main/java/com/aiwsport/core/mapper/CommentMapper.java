package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.Comment;
import com.aiwsport.core.entity.CommentBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer id);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByTemplateId(Integer templateId);

    List<Comment> getCommentByStoryId(Integer storyId);

    List<CommentBean> getCommentInfo(@Param("templateId") Integer templateId, @Param("storyId") Integer storyId);
}