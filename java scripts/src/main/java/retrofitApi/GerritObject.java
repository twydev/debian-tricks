package retrofitApi;

public class GerritObject {
    private long userId;
    private long id;
    private String title;
    private boolean completed;

    public long getUserId() {
        return userId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "\nuserId : " + userId + "\nid : " + id + "\ntitle : " + title + "\ncompleted : " + completed;
    }
}
