package com.example.android.newsapp;

/**
 * {@link News} represents the regularly-updated news from the Guardian API related to a particular topic.
 */
public class News {
    /** Title, section, author, date and URL of the article */
    private String articleTitle, articleSection, articleAuthor, articleDate, articleUrl;

    /**
     * Create a new News object (initialize the values)
     * @param articleTitle is the title of the article
     * @param articleSection is the section of the article
     * @param articleAuthor is the author of the article
     * @param articleDate is the date of publication of the article
     * @param articleUrl is the URL of the article
     */
    public News(String articleTitle, String articleSection, String articleAuthor, String articleDate, String articleUrl) {
        this.articleTitle = articleTitle;
        this.articleSection= articleSection;
        this.articleAuthor = articleAuthor;
        this.articleDate = articleDate;
        this.articleUrl = articleUrl;
    }

    /** Get the title of the article */
    public String getArticleTitle() {
        return articleTitle;
    }

    /** Get the section of the article */
    public String getArticleSection() {
        return  articleSection;
    }

    /** Get the author of the article */
    public String getArticleAuthor() {
        return articleAuthor;
    }

    /** Get the date of publication of the article */
    public String getArticleDate() {
        return articleDate;
    }

    /** Get the URL of the article */
    public String getArticleUrl() {
        return articleUrl;
    }
}