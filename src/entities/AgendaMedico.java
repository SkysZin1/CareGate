package entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgendaMedico {
    private String turno;
    private List<LocalTime> horarios;

    public AgendaMedico(String turno, List<LocalTime> horarios) {
        this.turno = turno;
        this.horarios = new ArrayList<>(horarios);
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public List<LocalTime> getHorarios() {
        return Collections.unmodifiableList(horarios);
    }

    public void setHorarios(List<LocalTime> horarios) {
        this.horarios = new ArrayList<>(horarios);
    }
}
