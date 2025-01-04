package pt.psoft.g1.psoftg1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.security.core.GrantedAuthority;

@Data
@Node
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
	@Id
	@GeneratedValue
	private Long id;

	public static final String ADMIN = "ADMIN";
	public static final String LIBRARIAN = "LIBRARIAN";
	public static final String READER = "READER";

	String authority;

	public Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}
