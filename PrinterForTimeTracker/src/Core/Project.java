package Core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project extends Activity{

  private List<Activity> childs;

  public Project(String name) {
    super(name);
    childs = new ArrayList<>();
  }

  public Project(String name, LocalDateTime initialDate, LocalDateTime finalDate, Duration duration) {
    super(name, initialDate, finalDate, duration);
    childs = new ArrayList<>();
  }

  @Override
  public void acceptVisitor(PrinterVisitor v) {
    v.visitProject(this);
  }

  public void addActivity(Activity a)
  {
    childs.add(a);
    a.addFather(this);
  }

  public void removeActivity(Activity a)
  {
    childs.remove(a);
  }

  public List<Activity> getChilds() {
    return childs;
  }

  public Activity find(String name) {
    if(name.equals(this.getName())) {
      return this;
    } else {
      for(Activity act : childs) {
        Activity activity = act.find(name);
        if(activity != null) {
          return activity;
        }
      }
    }
    return null;
  }
}
