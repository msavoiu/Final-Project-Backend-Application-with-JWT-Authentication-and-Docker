package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import entities.Blade;

public interface BladeRepository extends JpaRepository<Blade, Long> {

    // rewrite this query!!
    @Query("""
        SELECT new com.app.dtos.response.BookingResponseDTO(b)
        FROM Booking b
        WHERE b.attendee.attendeeId = :id
    """)
    public List<Blade> getAllForUser(@Param("username") String username);
}
