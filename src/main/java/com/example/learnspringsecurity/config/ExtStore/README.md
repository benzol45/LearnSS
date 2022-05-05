1. Создать класс с настроками extends WebSecurityConfigurerAdapter с @EnableWebSecurity
2. В нём реализуем два configure() c AuthenticationManagerBuilder auth и с HttpSecurity http
3. В configure(HttpSecurity http) делаем http.authorizeRequests().anyRequest().permitAll();
3a. Если надо REST то csrf().disable(), если консоль H2 .headers().frameOptions().disable()
4. Сделать регистрацию, дать к ней доступ всем, обеспечить сущность для хранения пользователей (логин, хеш пароля, роли)
5. Сделать бин с энкодером (разумно BCrypt)
6. SS понимает пользователей в виде UserDetails, сделать класс с имплементацией который будет создаваться из нашей сущности
7а. Роли нужные SS не строкой а в виде GrantedAuthority, самое порстое возвращать new SimpleGrantedAuthority("ROLE_ТутИмяРоли");
7. Сделать сервис (!@Service) с реализацией UserDetailsService который по логину будет возвращать этот самый UserDetails
8. Заавтовайрить этот сервис в конфиг SS и указать этот сервис для AuthenticationManagerBuilder через userDetailsService(БинСервисаСюда) и после него обязательно передать энкодер для паролей.
9a. Если будет авторизация в REST http.httpBasic()
9b. Если будет авторизация с формы http.formLogin()

Получить текущего залогиненного пользователя:
а) В контроллерах добавив в параметры "@AuthenticationPrincipal UserDetails details" или "Authentication auth" (но менее наглядно что это)
б) Хоть где через SecurityContextHolder.getContext().getAuthentication();
