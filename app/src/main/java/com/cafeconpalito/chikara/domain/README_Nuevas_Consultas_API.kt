package com.cafeconpalito.chikara.domain

class README_Nuevas_Consultas_API {

    /*

    CAPA DOMAIN

    1.- (Paquete "repository" + "<Name>Repository")
        Añadir al repositorio Existente o a uno nuevo los metodos que se quieren utilizar.
        Esta clase es una interfase, por lo cual solo se declaran pero no se implementan.
        Son de tipo Suspend ya que se lanzaran en otro hilo.

        Ejemplo:

        interfase LoginRepository

        suspend fun getLogin(user:String,password:String):String?

    CAPA DATA

    2.- (Paquete "service" + "<Name>ApiService")
        Añadir al Servicio existente o nuevo los metodos y la funcion web requerida.
        Esta clase es una interfase, por lo cual solo se declaran pero no se implementan.
        Son de tipo Suspend ya que se lanzaran en otro hilo.
        Es necesaria poner la notacion del metodo WEB requerido @GET @PUT @POST @DELETE y la ruta

        Ejemplo:

        interface LoginApiService

        @GET("/api/v1/users/login")
        suspend fun getLogin(@Query("user") user:String, @Query("password") password:String):String

    3.- (Paquete "repositoryImpl" + "<Name>RepositoryImpl")
        Añadir a la implementacion del Repositorio o uno nuevo los metodos que se van a utilizar.
        En esta caso al implemementar los metodos del repositorio de la capa "Domain",
        te pedira hacer Override de los mismos o los nuevos añadidos.
        Este es el que se ocupa de la logica y de devolver la informacion

        Ejemplo:


        //En el Constructor se injecta el servicio de la api para poder hacer las peticiones a la API
        //@Inject constructor(private val apiService: LoginApiService)

        //Extiende de LoginRepository para implementar sus metodos.
        // class LoginRepositoryImpl "....." Service) :LoginRepository //EL FINAL ES LA IMPLENTENCION


        class LoginRepositoryImpl @Inject constructor(private val apiService: LoginApiService):LoginRepository

        //Metodo para realizar el login
        //La respuesta esperada es un 200 con una Api Key

        override suspend fun getLogin(user: String, password: String): String? {

            //Lanza el intento de login
            runCatching {
                apiService.getLogin(user,password)
            }
                .onSuccess { return  it } //En caso de exito (200) y que el dato que llega es correcto, realiza lo que esta entre corchetes
                .onFailure { Log.i ("getLogin", "IMPOSIBLE LOGUEAR: ${it.toString()}") } //En caso de fallo lo que este entre corchetes.

            return null

        }

    4.- (Paquete "network" + "NetworkModule")
        !!!! SOLO SI LOS TRES PASOS ANTERIORES CREARON NUEVAS CLASES ES NECESARIO !!!!
        Es necesario añadir a estos dos metodos Ajustandolos alas clases que creaste.
        Uno se Ocupa de construir el ApiService y el otro el repositorio.

        Ejemplo:

        //Construye el repositorio de API SERVICE, con la informacion Retrofit
        //Retrofit: Encargado de conectarse a la API
        @Provides
        fun provider<Name>ApiService(retrofit: Retrofit):<Name>ApiService{
            return retrofit.create(<Name>ApiService::class.java)
        }

        //Contruye el servicio para poder consumir de la Api
        @Provides
        fun provide<Name>Repository(apiService: <Name>ApiService):<Name>Repository {
            return LoginRepositoryImpl(apiService)
        }



        CON ESTO YA TENEMOS LA IMPLEMENTACION DE LAS CONSULTAS A LA API DESDE DATA. AHORA ES DOMINIO EL QUE PASARA LOS DATOS AL UI

    CAPA DOMAIN

    5.- (Paquete "useCase" + <Name>UseCase)
        Se ocupara de ser el puente entre la capa de Data y Ui.
        Creara los metodos necesarios para trasmitir la informacion.

        !!! DUDA DE DANIEL ¡¡¡¡
        agrupamos metodos por clases y los invocamos en funcion de lo que necesitemos
        o por cada metodo creado arriba generamos un <name>useCase con *operator. lo cual con llamar a la clase directamente invoca al metodo...

        IDEA OPERATOR. en este caso el login es muy simple y si que tendria sentido solo contiene un metodo y el nombre de la clase representa lo que hace:

                //Injecta el repositorio para realizar la consulta
                class GetLoginUseCase @Inject constructor(private val repository: LoginRepository) {

                //al llamar a la clase llama directamente al metodo
                suspend operator fun invoke(user:String , password:String) = repository.getLogin(user,password)
                }

        IDEA POR CLASES, en cada clase mantener los metodos necesarios y llamarlos cuando se necesiten.

                //Injecta el repositorio para realizar la consulta
                class LoginUseCase @Inject constructor(private val repository: LoginRepository) {

                // llama al getLogin
                suspend fun getLogin(user:String , password:String) = repository.getLogin(user,password)
                }

                // Metodo invent!
                suspend fun metodoInvent(numero:Int) = repository.metodoInvent(numero)



     CAPA UI

     6.- Uso de las peticiones desde el front tenemos tres casos, si es Un Main Activity, Fragmento , o Recicler View

        6.1.- FRAGMENTO:

            Es necesario

            @Inject
            lateinit var getLogin: GetLogin // Injected dependency

            @Inject
            lateinit var getChicksUseCase: GetChicksUseCase




     */


}