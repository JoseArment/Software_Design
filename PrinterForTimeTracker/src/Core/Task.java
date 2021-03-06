package Core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends Activity{

  private List<Interval> intervals;
  private boolean status;

  public Task(String name) {
    super(name);
    intervals = new ArrayList<>();
  }

  public Task(String name, LocalDateTime initialDate, LocalDateTime finalDate, Duration duration) {
    super(name, initialDate, finalDate, duration);
    intervals = new ArrayList<>();
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public void startTask() {
    LocalDateTime newInitialDate = Clock.getInstance().getDate();
    Interval newInterval = new Interval(this);
    intervals.add(newInterval);
    Clock.getInstance().addObserver(newInterval);
    if(getInitialDate() == null) {
      this.setInitialDate(newInitialDate);
    }
    this.status = true;
  }

  public void stopTask() {
    Clock.getInstance().deleteObserver(intervals.get(intervals.size()-1));
    this.status = false;
  }

  public List<Interval> getIntervals() { return this.intervals; }

  @Override
  public String toString() {
    String description = intervals.get(intervals.size()-1).toString();
    return description+super.toString();
  }

  @Override
  public void acceptVisitor(PrinterVisitor v) {
    v.visitTask(this);
  }

  public void addInterval(Interval interval) {
    intervals.add(interval);
    interval.setFather(this);
  }

  public Activity find(String name) {
    if(name.equals(this.getName())) {
      return this;
    } else {
      return null;
    }
  }
}
