package com.aiwsport.core.mapper;

import com.aiwsport.core.entity.Comment;
import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer id);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByTemplateId(Integer templateId);

    List<Comment> getCommentByStoryId(Integer storyId);


}