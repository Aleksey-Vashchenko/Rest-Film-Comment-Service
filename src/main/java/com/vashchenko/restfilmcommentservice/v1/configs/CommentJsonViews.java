package com.vashchenko.restfilmcommentservice.v1.configs;

public class CommentJsonViews {
    public interface UserCommentView extends DefaultView,FilmJsonViews.DefaultView {};
    public interface FilmCommentView extends DefaultView,UserJsonViews.DefaultView{};
    public interface DefaultView{};
}
