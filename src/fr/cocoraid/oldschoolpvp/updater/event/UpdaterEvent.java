package fr.cocoraid.oldschoolpvp.updater.event;

import fr.cocoraid.oldschoolpvp.updater.UpdateType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdaterEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private UpdateType _type;
  
  public UpdaterEvent(UpdateType example)
  {
    this._type = example;
  }
  
  public UpdateType getType()
  {
    return this._type;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
}
