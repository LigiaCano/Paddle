package business.wrapper;

import data.entities.Court;

public class CourtState {

    private int courtId;

    private boolean active;

    public CourtState() {
    }

    public CourtState(int courtId, boolean active) {
        super();
        this.courtId = courtId;
        this.active = active;
    }

    public CourtState(Court court) {
        this(court.getId(), court.isActive());
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "CourtStateWrapper [courtId=" + courtId + ", active=" + active + "]";
    }

}
