package com.burhan.userorg;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;

@SpringBootTest()
@AutoConfigureMockMvc
class UserOrgApplicationTests {


	private MockMvc mockMvc;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private String jwtToken;

	String jsonContent = "{ \"userName\": \"Test\", \"age\": 22, \"email\": \"test@gmail.com\", \"organizationId\": 1, \"password\": \"test\", \"role\": \"ROLE_USER\" }";

	String jsonContent2 = "{ \"id\": \"40\",\"userName\": \"Test2\", \"age\": 22, \"email\": \"test2@gmail.com\", \"organizationId\": 1, \"password\": \"test\", \"role\": \"ROLE_USER\" }";

	@BeforeEach
	public void setUp() {
		// Generate a JWT token with a valid user
		String username = "burhan12";
		String role = "ROLE_ADMIN";

		jwtToken = Jwts.builder()
				.setSubject(username)
				.claim("role", role)
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(SignatureAlgorithm.HS512, "jwtTokenKey")
				.compact();
	}

	@Test
	public void testFindAllUsersWithValidToken() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users")
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}


	@Test
	public void testFindUserByOrgId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/org/4")
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}

	@Test
	public void testSaveUsers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent)
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent2)
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/39")
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}


				//Organization test cases
	@Test
	public void testfindAllOrganizations() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/organization")
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}
	@Test
	public void testFindOrganizationById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/organization/1")
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindAllUsersById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/organization/allUsers/4")
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}

	@Test
	public void testSaveOrganization() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/organization")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent)
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateOrganization() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/organization")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent2)
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteOrganization() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/organization/39")
				.header("Authorization", "Bearer " + jwtToken))
				.andExpect(status().isOk());
	}
}
