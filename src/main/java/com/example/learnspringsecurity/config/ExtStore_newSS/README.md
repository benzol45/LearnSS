1. Создать класс с настроками extends WebSecurityConfigurerAdapter и в нём @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
2. В нём http. и настраиваем что надо, например делаем http.authorizeRequests().anyRequest().permitAll() 
3. потом return http.build();
4. Если надо REST то csrf().disable(), если консоль H2 .headers().frameOptions().disable()  
5. Сделать регистрацию, дать к ней доступ всем, обеспечить сущность для хранения пользователей (логин, хеш пароля, роли)
6. Сделать бин с энкодером (разумно BCrypt)
7. SS понимает пользователей в виде UserDetails, сделать класс с имплементацией который будет создаваться из нашей сущности
7а. Роли нужные SS не строкой а в виде GrantedAuthority, самое простое возвращать new SimpleGrantedAuthority("ROLE_ТутИмяРоли");
8. Сделать сервис (!@Service) с реализацией UserDetailsService который по логину будет возвращать этот самый UserDetails
9. Создать бин @Bean public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) {
   return http.getSharedObject(AuthenticationManagerBuilder.class)
   .userDetailsService(userDetailsService)
   .passwordEncoder(bCryptPasswordEncoder)
   .and()
   .build();
   }
9a. Если будет авторизация в REST http.httpBasic()
9b. Если будет авторизация с формы http.formLogin()

Описание доступа к странице:
1) Через HttpSecurity http. добавляем вида .antMatchers("/admin/**").hasAnyRole("ADMIN")
2) На методах @PreAuthorize("hasRole('ROLE_VIEWER')") или HasAuthority('VIEWER') или hasAnyRole или hasAnyAuthority('DELETE', 'UPDATE') !!! НО ДЛЯ ЭТОГО @EnableGlobalMethodSecurity(prePostEnabled = true) в конфиге

Получить текущего залогиненного пользователя:
а) В контроллерах добавив в параметры "@AuthenticationPrincipal UserDetails details" или "Authentication auth" (но менее наглядно что это)
б) Хоть где через SecurityContextHolder.getContext().getAuthentication();

PS можно использовать jdbcAuthentication если имена таблиц users и authorities и тогда нужно только указать бин-datasource и все запросы/преобразования SS сделает сам. Пример в курсе у Заура, урок 93
