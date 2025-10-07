package com.mynewkraf.api;

import java.util.List;

public interface HelloService {
	String say(String name);
	String createProfile(Employee ee);
	String deleteProfile(String id);
	String editProfile(String id,Employee ee);
	String viewProfile(String id);
	List<Employee> viewAllProfile();
}
