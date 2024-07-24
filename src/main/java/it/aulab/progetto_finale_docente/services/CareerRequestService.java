package it.aulab.progetto_finale_docente.services;

import it.aulab.progetto_finale_docente.models.CarreerRequest;
import it.aulab.progetto_finale_docente.models.User;

public interface CareerRequestService {
    boolean isRoleAlreadyAssigned(User user, CarreerRequest careerRequest);
    void save(CarreerRequest carrerRequest, User user);
    void carrerAccept(Long requestId);
    CarreerRequest find(Long id);
}