package br.com.roanrobersson.rshop.domain.event;

import org.springframework.context.ApplicationEvent;

import br.com.roanrobersson.rshop.domain.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "user" })
public class OnRegistrationCompleteEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	
	private User user;

	public OnRegistrationCompleteEvent(User user) {
		super(user);
		this.user = user;
	}
}