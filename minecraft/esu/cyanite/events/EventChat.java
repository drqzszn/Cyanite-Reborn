package esu.cyanite.events;

import com.darkmagician6.eventapi.events.Event;

public class EventChat implements Event{
    public byte type;

    private String message;


    public EventChat(String message) {
            this.message = message;
            this.setType((byte)0);
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    public class Type {
        public static final byte PRE = 0;
        public static final byte POST = 1;
        public static final byte OUTGOING = 2;
        public static final byte INCOMING = 3;
    }
    public void setType(byte type) {
        this.type = type;
    }
}
