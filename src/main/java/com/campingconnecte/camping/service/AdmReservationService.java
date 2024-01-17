package com.campingconnecte.camping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.campingconnecte.camping.dto.AdmReservationDTO;
import com.campingconnecte.camping.model.Reservation;
import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.model.User;
import com.campingconnecte.camping.repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdmReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;

    public List<AdmReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AdmReservationDTO getReservationById(int id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        return convertToDTO(reservation);
    }

    public void saveReservation(AdmReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);
        reservationRepository.save(reservation);
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }

    private AdmReservationDTO convertToDTO(Reservation reservation) {
        AdmReservationDTO dto = new AdmReservationDTO();
        dto.setId(reservation.getId());

        // Vérifier si l'utilisateur et le site ne sont pas null avant d'accéder à leurs IDs
        if (reservation.getUser() != null) {
            dto.setUserId(reservation.getUser().getId());
        }
        if (reservation.getSite() != null) {
            dto.setSiteId(reservation.getSite().getId());
        }

        dto.setDateDebut(reservation.getDateDebut());
        dto.setDateFin(reservation.getDateFin());

        // Gérer le cas où nombreDeNuits est null
        Integer nombreDeNuits = reservation.getNombreDeNuits();
        dto.setNombreDeNuits(nombreDeNuits != null ? nombreDeNuits : 0);

        dto.setPrixTotal(reservation.getPrixTotal());
        dto.setStatus(reservation.getStatus());
        return dto;
    }

    private Reservation convertToEntity(AdmReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());

        User user = userService.getUserById(reservationDTO.getUserId());
        Site site = siteService.getSiteById(reservationDTO.getSiteId());

        reservation.setUser(user);
        reservation.setSite(site);
        reservation.setDateDebut(reservationDTO.getDateDebut());
        reservation.setDateFin(reservationDTO.getDateFin());
        reservation.setNombreDeNuits(reservationDTO.getNombreDeNuits());
        reservation.setPrixTotal(reservationDTO.getPrixTotal());
        reservation.setStatus(reservationDTO.getStatus());

        return reservation;
    }
}


/*mercredi 17 janvier 7h00
@Service
public class AdmReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;
    public List<AdmReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AdmReservationDTO getReservationById(int id) {
    	Reservation reservation = reservationRepository.findById(id).orElse(null);
    	return convertToDTO(reservation);
    	}
    public void saveReservation(AdmReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);
        reservationRepository.save(reservation);
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }

    private AdmReservationDTO convertToDTO(Reservation reservation) {
        AdmReservationDTO dto = new AdmReservationDTO();
        // Conversion de Reservation en AdmReservationDTO
        // Implémentez cette méthode selon la structure de vos classes
        dto.setId(reservation.getId());
        dto.setUserId(reservation.getUser().getId()); // Assurez-vous que getUser() ne retourne pas null
        dto.setSiteId(reservation.getSite().getId()); // De même pour getSite()
        dto.setDateDebut(reservation.getDateDebut());
        dto.setDateFin(reservation.getDateFin());
        dto.setNombreDeNuits(reservation.getNombreDeNuits());
        dto.setPrixTotal(reservation.getPrixTotal());
        dto.setStatus(reservation.getStatus());
return dto;
    }

    private Reservation convertToEntity(AdmReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());

        // Ici, vous devez récupérer les entités User et Site par leur ID
        // Exemple (assurez-vous que les méthodes getUserById et getSiteById existent)
        User user = userService.getUserById(reservationDTO.getUserId());
        Site site = siteService.getSiteById(reservationDTO.getSiteId());

        reservation.setUser(user);
        reservation.setSite(site);
        reservation.setDateDebut(reservationDTO.getDateDebut());
        reservation.setDateFin(reservationDTO.getDateFin());
        reservation.setNombreDeNuits(reservationDTO.getNombreDeNuits());
        reservation.setPrixTotal(reservationDTO.getPrixTotal());
        reservation.setStatus(reservationDTO.getStatus());

        return reservation;
    }
}*/