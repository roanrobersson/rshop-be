package br.com.roanrobersson.rshop.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

	public @interface Address {

		@PreAuthorize("isAuthenticated() and @authService.authenticatedUserIdEquals(#userId) or "
				+ "hasAuthority('CONSULT_ADDRESSES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanConsult {
		}

		@PreAuthorize("isAuthenticated() and @authService.authenticatedUserIdEquals(#userId) or "
				+ "hasAuthority('EDIT_ADDRESSES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {
		}
	}

	public @interface Category {

		@PreAuthorize("isAuthenticated() and hasAuthority('CONSULT_CATEGORIES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanConsult {
		}

		@PreAuthorize("isAuthenticated() and hasAuthority('EDIT_CATEGORIES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {
		}
	}

	public @interface File {

		@PreAuthorize("isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {
		}
	}

	public @interface Product {

		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanConsult {
		}

		@PreAuthorize("isAuthenticated() and hasAuthority('EDIT_PRODUCTS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {
		}
	}

	public @interface Role {

		@PreAuthorize("isAuthenticated() and @authService.authenticatedUserIdEquals(#userId) or "
				+ "hasAuthority('CONSULT_ROLES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanConsult {
		}

		@PreAuthorize("isAuthenticated() and hasAuthority('EDIT_ROLES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {
		}
	}

	public @interface User {

		@PreAuthorize("isAuthenticated() and @authService.authenticatedUserIdEquals(#userId) or "
				+ "hasAuthority('CONSULT_USERS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanConsult {
		}

		@PreAuthorize("isAuthenticated() and @authService.authenticatedUserIdEquals(#userId) or "
				+ "hasAuthority('EDIT_USERS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanEdit {
		}
	}

	public @interface UserRole {

		@PreAuthorize("isAuthenticated() and @authService.authenticatedUserIdEquals(#userId) or "
				+ "hasAuthority('CONSULT_USER_ROLES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanConsult {
		}

		@PreAuthorize("isAuthenticated() and hasAuthority('EDIT_USER_ROLES')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface CanGrantAndRevoke {
		}
	}
}
