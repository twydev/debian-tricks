package kyloApi;

import java.util.ArrayList;
import java.util.List;

public class JobBins {
    List<Long> beforeTime = new ArrayList<>();
    List<Long> afterTime = new ArrayList<>();

    public void addBefore(long id) {
        beforeTime.add(id);
    }

    public void addAfter(long id) {
        afterTime.add(id);
    }

    @Override
    public String toString() {
        return "before : " + beforeTime.size() + " | after : " + afterTime.size();
    }

    public List<Long> getBeforeTime() {
        return beforeTime;
    }

    public List<Long> getAfterTime() {
        return afterTime;
    }
}