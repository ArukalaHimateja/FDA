package com.fda.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fda.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public boolean existsByMobileNumber(String mobileNumber);

	public User findByMobileNumber(String username);

	public List<User> findByRole(int i);

	public boolean existsByMobileNumberAndRole(String mobileNumber, Integer role);

	public User findByMobileNumberAndRole(String username, Integer role);

	public User findByMobileNumberOrEmail(String mobileNumber, String email);

	public boolean existsByEmail(String email);

	public User findByEmailOrMobileNumber(String username, String username2);

	public long countByRole(int i);

	public Optional<User> findByIdAndRole(long id, int i);

	public User findByMobileNumberOrEmailAndPassword(String username, String username2, String password);

	public User findByEmail(String email);

	public List<User> findByRoleIn(List<Integer> roleList);

	public List<User> findByRoleInAndIdNotIn(List<Integer> roleList, List<Long> userList);

	public boolean existsByIdAndRole(Long customerId, int i);

}
