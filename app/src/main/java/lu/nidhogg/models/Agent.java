package lu.nidhogg.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Agent {

	public static final Agent	DEFAULT	= new Agent("Minecraft", 1);

	private String				name;

	private int					version;

}
