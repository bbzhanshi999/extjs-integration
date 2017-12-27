package com.zql.common;

import com.google.common.collect.Maps;
import com.zql.ExtjsIntegrationApplication;
import com.zql.common.dao.SystemDao;
import com.zql.common.entity.Role;
import com.zql.common.entity.User;
import com.zql.common.service.UserService;
import com.zql.common.service.SystemService;
import com.zql.common.utils.IdGenrator;
import com.zql.common.utils.SnowflakeIdWorker;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExtjsIntegrationApplication.class)
public class ExtjsIntegrationApplicationTests {

	/*@Autowired
	public PasswordEncoder passwordEncoder;

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private SystemDao systemDao;

	@Autowired
	private RedisConnectionFactory factory;

	@Autowired
	SystemService systemService;
*/
	/*@Autowired
	UserService userService;*/

    @Test
    public void contextLoads() {
    }
	/*@Test
	public void contextLoads() {
		String encode = passwordEncoder.encode("123456");
		System.out.println(encode);
	}
	@Test
	public void idTest(){
		SnowflakeIdWorker worker = new SnowflakeIdWorker(0,31);
		long l = worker.nextId();
		long l1 = worker.nextId();
		long l2 = worker.nextId();
		System.out.println(l);
		System.out.println(l1);
		System.out.println(l2);
	}

	@Test
	public void testId(){
        System.out.println(IdGenrator.snowFlakeId());
	}

	@Test
	public void streamTest(){
		List<Map<String, Object>> list = jdbcTemplate.getJdbcOperations().query("SELECT a.id,a.username,c.id as \"role_id\", " +
				"c.role_name,e.id as \"depart_id\",e.depart_name,g.id as \"post_id\",g.post_name FROM sys_user a " +
				"LEFT JOIN sys_user_role b ON a.id = b.user_id " +
				"LEFT JOIN sys_role c ON c.id = b.role_id " +
				"LEFT JOIN sys_user_department d ON d.user_id = a.id " +
				"LEFT JOIN sys_department e ON e.id = d.depart_id " +
				"LEFT JOIN sys_user_post f ON f.user_id = a.id " +
				"LEFT JOIN sys_post g ON g.id = f.post_id " +
				"WHERE a.username='zql'", (rs, i) -> {
                    Map<String,Object> map = Maps.newHashMap();
                    map.put("username",rs.getString("username"));
                    map.put("rol.eName",rs.getString("role_name"));
                    map.put("departName",rs.getString("depart_name"));
                    map.put("postName",rs.getString("post_name"));
                    map.put("role_id",rs.getLong("role_id"));
                    map.put("depart_id",rs.getLong("depart_id"));
                    map.put("post_id",rs.getLong("post_id"));
                    return map;
                });
		Map<Long, List<Map<String, Object>>> roleGroup = list.stream().collect(Collectors.groupingBy(o -> (Long)o.get("role_id")));
		Map<Long, List<Map<String, Object>>> departGroup = list.stream().collect(Collectors.groupingBy(o -> (Long)o.get("depart_id")));
		Map<Long, List<Map<String, Object>>> postGroup = list.stream().collect(Collectors.groupingBy(o -> (Long)o.get("post_id")));
		List<Role> roles = Lists.newArrayList();
		for(Long key:roleGroup.keySet()){
			List<Map<String, Object>> mapList = roleGroup.get(key);
			Role r = new Role();
			r.setId((Long)mapList.get(0).get("role_id"));
		}
	}

	@Test
	public void listTest(){
		List<String> al = Arrays.asList("1","2","3","4");
		al.addAll(null);
		System.out.println(al);
	}

	@Test
    public void redisGetTest(){
        List roles = redisTemplate.opsForHash().values("roles");
        roles.forEach(System.out::println);
        Jedis nativeConnection = (Jedis) factory.getConnection().getNativeConnection();
        nativeConnection.flushAll();
    }

    @Test
    public void cacheTest() throws InterruptedException {
        User zql = systemDao.findUserByUsername("zql");
        Thread.sleep(10000);
        User zql2 = systemDao.findUserByUsername("zql");
    }

    @Test
    public void roleTest(){
		systemDao.clearAuthCache();
    }

    @Test
	public void voteTest(){
		userService.loadUserByUsername("zql");
	}
*/
}

