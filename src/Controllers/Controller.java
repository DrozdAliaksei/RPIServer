package Controllers;

public class Controller {

    private int ID;
    private String name;
    private int GPIO;
    private boolean status;

    public Controller(int ID, String name, int GPIO, boolean status) {
        this.ID = ID;
        this.name = name;
        this.GPIO = GPIO;
        this.status = status;
    }
    
    @Override
    public String toString() {
        return   ID +
                ";" + name + 
                ";" + GPIO +
                ";" + status;	
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGPIO() {
        return GPIO;
    }

    public void setGPIO(int GPIO) {
        this.GPIO = GPIO;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }
}
