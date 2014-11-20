package jk.web.user.repository;

import java.util.HashSet;
import java.util.Set;

import jk.web.user.entities.LoginEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;

public interface LoginRepository  extends JpaRepository<LoginEntity, Long>  {

	public enum Permission implements GrantedAuthority{
		ALL					(1	 ),
		DEFAULT				(1<<1),
		LOCKED				(1<<2),
		CREDENTIALS_EXPIRED	(1<<3),
		DISABLED			(1<<4),
		ACCOUNT_EXPIRED		(1<<5),
		NEW					(1<<6);

		private long permission;

		private Permission(int permission){
			this.permission = permission;
		}

		@Override
		public String getAuthority() {
			return name();
		}

		public long toLong(){
			return permission;
		}

		public static Long toLong(Set<Permission> permissionsList){
			Long permissions;
			if(permissionsList==null || permissionsList.isEmpty())
				permissions = null;
			else{
				permissions = 0L;
				for(Permission p:permissionsList)
					permissions += Long.MAX_VALUE & p.permission;
			}

			return permissions;
		}

		public static Set<Permission> toPermissions(Long permissions){
			Set<Permission> set;

			if(permissions==null || permissions==0)
				set = null;
			else{
				set = new HashSet<Permission>();
				for(Permission p:Permission.values())
					if((p.permission&permissions)!=0)
						set.add(p);
			}

			return set;
		}

		public static boolean hasPermission(Permission permissions, Permission permissionToCheckFor){
			return permissions!=null && (permissions.permission&permissionToCheckFor.permission)>0;
		}

		public static Long addPermission(Long permissions, Permission permissionToAdd){
			return permissions!=null ? permissions|permissionToAdd.permission : permissionToAdd.permission;
		}

		public static Long removePermission(Long permissions, Permission permissionToAdd){
			return permissions!=null ? permissions&(-1l ^ permissionToAdd.permission) : null;
		}
	}

	public LoginEntity findByUsername(String username);

	@Query("SELECT count(l.id)>0 FROM login l WHERE l.username = ?1")
	public boolean exists(String username);

	@Query("UPDATE login l SET l.username=:username WHERE l.id=:id")
	public int saveUsernae(Long id, String username);

	@Query(value="SELECT l FROM login l LEFT JOIN l.emails e WHERE l.username=?1 or e.eMail=?1")
	public LoginEntity findByUsernameOrEMail(String usernameOrEMail);

	public LoginEntity findById(Long userId);
}
