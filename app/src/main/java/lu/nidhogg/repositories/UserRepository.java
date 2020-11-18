package lu.nidhogg.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import lu.nidhogg.entities.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, UUID> {

	Optional<UserEntity> findByUsername(String username);

	Optional <UserEntity> findByEmail(String username);
	
}
