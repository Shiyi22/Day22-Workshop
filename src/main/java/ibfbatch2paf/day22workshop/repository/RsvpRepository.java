package ibfbatch2paf.day22workshop.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibfbatch2paf.day22workshop.model.RSVP;

@Repository
public class RsvpRepository {

    @Autowired
    private JdbcTemplate template;

    String countSQL = "select count(*) from rsvp"; 
    String selectAllSQL = "select * from rsvp"; 
    String selectByIdSQL = "select * from rsvp where id = ?";
    String selectByNameSQL = "select * from rsvp where full_name like ?";  
    String selectByEmailSQL = "select * from rsvp where email = ?";  
    String insertSQL = "insert into rsvp (full_name, email, phone, confirmation_date, comments) values (? , ?, ?, ?, ?)"; 
    String updateSQL = "update rsvp set full_name = ?, email = ?, phone = ?, confirmation_date = ?, comments = ? where id = ?";

    public int countAll() {
        return template.queryForObject(countSQL, Integer.class); 
    }

    public List<RSVP> findAll () {
        // beanPropertyRowMapper is required to map column name into class
        return template.query(selectAllSQL, new BeanPropertyRowMapper<>(RSVP.class));
    }
    
    public RSVP findById(int id) {
        return template.queryForObject(selectByIdSQL, BeanPropertyRowMapper.newInstance(RSVP.class), id); 
    }

    public List<RSVP> findByName(String fullName) {
        // changed 
        return template.query(selectByNameSQL, new BeanPropertyRowMapper<>(RSVP.class), "%" + fullName + "%"); 
        // return template.queryForObject(selectByNameSQL, RSVP.class, fullName); 
    }

    public Optional<RSVP> findByEmail(String email) {
        try {
            RSVP rsvp = template.queryForObject(selectByEmailSQL, BeanPropertyRowMapper.newInstance(RSVP.class), email);
            return Optional.of(rsvp); 
        } catch (DataAccessException ex) {
            return Optional.empty();
        }    
    }

    public Boolean save(RSVP rsvp) {
        Integer iResult = template.update(insertSQL, rsvp.getFullName(), rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments());
        return iResult > 0 ? true : false; 
    }

    public Boolean update(RSVP rsvp) {
        Integer iResult = template.update(updateSQL, rsvp.getFullName(), rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments(), rsvp.getId()); 
        return iResult > 0 ? true : false; 
    }

    // add a bunch of new RSVP
    // returns an array of integer 
    public int[] batchUpdate(List<RSVP> rsvps) {
        
        return template.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
            
            public void setValues (PreparedStatement ps, int i) throws SQLException {

                ps.setString(1, rsvps.get(i).getFullName());
                ps.setString(2, rsvps.get(i).getEmail());
                ps.setString(3, rsvps.get(i).getPhone());
                ps.setDate(4, rsvps.get(i).getConfirmationDate());
                ps.setString(5, rsvps.get(i).getComments());
            }
            public int getBatchSize() {
                return rsvps.size(); 
            }
        });
        
    }
    
}
