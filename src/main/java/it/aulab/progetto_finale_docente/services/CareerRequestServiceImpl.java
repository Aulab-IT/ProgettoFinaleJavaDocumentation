package it.aulab.progetto_finale_docente.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aulab.progetto_finale_docente.models.CarreerRequest;
import it.aulab.progetto_finale_docente.models.Role;
import it.aulab.progetto_finale_docente.models.User;
import it.aulab.progetto_finale_docente.repositories.CarreerRequestRepository;
import it.aulab.progetto_finale_docente.repositories.RoleRepository;
import it.aulab.progetto_finale_docente.repositories.UserRepository;

import java.util.List;

@Service
public class CareerRequestServiceImpl implements CareerRequestService{

    @Autowired
    private CarreerRequestRepository carreerRequestRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public boolean isRoleAlreadyAssigned(User user, CarreerRequest careerRequest) {
        List<Long> allUserIds = carreerRequestRepository.findAllUserIds();

        if (!allUserIds.contains(user.getId())) {
            return false;
        }

        List<Long> requests = carreerRequestRepository.findByUserId(user.getId());

        return requests.stream().anyMatch(roleId -> roleId.equals(careerRequest.getRole().getId()));
    }

    public void save(CarreerRequest carrerRequest, User user){
        carrerRequest.setUser(user);
        carrerRequest.setIsChecked(false);
        carreerRequestRepository.save(carrerRequest);

        //Invio mail di richiesta del ruolo all'admin
        emailService.sendSimpleEmail("adminAulabpost@admin.com", "Richiesta per ruolo: " + carrerRequest.getRole().getName(), "C'è una nuova richiesta di collaborazione da parte di " + user.getUsername());
    }

    @Override
    public void carrerAccept(Long requestId) {
        //Recupero la richiesta
        CarreerRequest request = carreerRequestRepository.findById(requestId).get();
        
        //Dalla richiesta estraggo l'utente richiedente ed il ruolo richiesto
        User user = request.getUser();
        Role role = request.getRole();
        
        //Recupero tutti i ruoli che l'utente già possiede ed aggiungo quello nuovo
        List<Role> rolesUser = user.getRoles();
        Role newRole = roleRepository.findByName(role.getName());
        rolesUser.add(newRole);

        //salvo tutte le nuove modifiche
        user.setRoles(rolesUser);
        userRepository.save(user);
        request.setIsChecked(true);
        carreerRequestRepository.save(request);

        emailService.sendSimpleEmail( user.getEmail() , "Ruolo abilitato" ,"Ciao, la tua richiesta di collaborazione è stata accettata dalla nostra amministrazione");
    }

    @Override
    public CarreerRequest find(Long id) {
        return carreerRequestRepository.findById(id).get();
    }
}