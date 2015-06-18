package com.xxu.rest;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xxu.client.GroupClient;

;

/**
 * @author xxu
 *
 *         should execute the test in the order of their name
 */
public class GroupCommServiceTest {

	private static GroupClient client1;
	private static GroupClient client2;

	/**
	 * @throws Exception
	 * 
	 *             private.key and public.key are a pair private_2.key and
	 *             public_2.key are a pair
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client1 = new GroupClient("1", "Private.key");
		client2 = new GroupClient("2", "Private_2.key");
	}

	/**
	 * @throws Exception
	 * 
	 *             should success
	 */
	@Test
	public void test() throws Exception {
		client1.updateKeyList();
		String outputServer = client1.readResponseFromServer();
		Assert.assertEquals(outputServer, "This is message for group member");
	}

	/**
	 * @throws Exception
	 * 
	 *             should fail(return null from client side), because a new key
	 *             is generated when there is a call to remove, but the client
	 *             is not informed of this change and do not update his keylist
	 */
	@Test
	public void test2() throws Exception {
		/*notice the server that a usr is removed form the group*/
		ClientRequest request = new ClientRequest(
				"http://localhost:8080/RESTfulExample/rest" + "/group/remove");
		ClientResponse<String> response = request.get(String.class);
		
		String outputServer = client1.readResponseFromServer();
		client1.readResponseFromServer();
		
		Assert.assertEquals(outputServer, null);
	}

	/**
	 * @throws Exception
	 * 
	 *             should success, because now the client tried to update the
	 *             key list and he gets the newest key
	 */
	@Test
	public void test3() throws Exception {
		client1.updateKeyList();
		String outputServer = client1.readResponseFromServer();
		Assert.assertEquals(outputServer, "This is message for group member");
	}

	/**
	 * @throws Exception
	 * 
	 *             should success because client2 is also in the group, so he
	 *             should also be able to decrypt the data , just like client 1
	 */
	@Test
	public void test4() throws Exception {
		client2.updateKeyList();
		String outputServer = client2.readResponseFromServer();
		Assert.assertEquals(outputServer, "This is message for group member");
	}

}
