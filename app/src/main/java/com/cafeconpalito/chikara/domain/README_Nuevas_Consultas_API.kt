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

            Es necesario:

            * Injectar como lateinit var el USE CASE, Encargado de realizar las consultas a la Api desde el dominio.

            @Inject
            lateinit var <name>UseCase: <name>UseCase // Injected dependency

            * Desde una funcion y dentro de una CoroutineScope se lanza la peticion y se espera respuesta.

            OJO INCONVENIENTE DE ESTE METODO NO PERMITE MODIFICAR LA VISTA
            YA QUE ESTA TRABAJANDO EN UN HILO SEPARADO

               Ejemplo:

               fun loggin() {

                    val user = "@usuario1"
                    val password = "a722c63db8ec8625af6cf71cb8c2d939"

                    //Lanza un hilo
                    CoroutineScope(Dispatchers.IO).launch {

                        if ( getLoginUseCase(user, password)) {
                            Log.i("Fragment Nakama: ", " Login Correcto")

                        } else {
                            Log.i("Fragment Nakama: ", " Login Incorrecto")
                        }
                    }
                }

            * PARA SOLUCIONARLO SE REALIZA LO SIGUIENTE.

            Se crea una clase State, esta reflejara los estados posibles y la informacion que devuelven.

            Ejemplo:

            sealed class <name>State {

                //Se ocupa del Estado de loading
                data object Loading: <name>State()
                //Al cambiar el estado a error enviara los datos que sean necesarios
                data class Error(val error:String): <name>State()
                //Al cambiar al estado de Success nos enciara los datos que necesitemos.
                data class Success(val apiKey:String): <name>State()

            }

            Se crea una clase de tipo ViewModel se encargara de controlar los estados.
            Se le injecta el constructor de los Use Case necesarios.


            Ejemplo:

            @HiltViewModel
            class <name>ViewModel @Inject constructor(private val <name>UseCase: <name>UseCase) : ViewModel(){


                //Como valor inicial le paso el estado de Cargando
                private var _state = MutableStateFlow< <name>State>(<name>State.Loading)
                //devuelve el estado en el que se encuentra, por eso no es publica
                val state: StateFlow< <name>State > = _state


                Se ocupa de intentar logear y cambia el estado en concecuencia

                fun tryLogin(user: String, password: String){

                    //Esta trabajando en el Hilo principal
                    viewModelScope.launch {

                        //Cambio el stado a loading.
                        _state.value = NakamaState.Loading

                        //Esto lanza un hilo secundario, almaceno la respuesta en result
                        val result = withContext(Dispatchers.IO){
                            <name>UseCase(user,password)
                        }

                        //si la respuesta es correcta
                        if (result){
                            //Paso el esdado a Success y le paso el texto del horoscopo
                            //SE PUEDE DEVOLVER EL OBJETO ENTERO O LO QUE NECESITEMOS.
                            _state.value = NakamaState.Success(result.toString())
                        } else{
                            _state.value = NakamaState.Error("No me logro conectar")
                        }

                    }
                }

            }

            En el Fragmento quedaria crear un metodo que se ocupe de lanzar al modelo y modificar la vista
            Es necesario inicializar initUIState() dentro del OnCreate del Fragmento.
            Utiliza un lifecycleScope.launch {} para lanzar la Corutina.

            Ejemplo: Codigo necesario dentro del fragmento.

            //Necesario para Que loggin2 Funcione
            private val <name>ViewModel:<name>ViewModel by viewModels()

            //Parte de el inicio de la UI para que este pendiende si cambia el estado al llamar al metodo.
            private fun initUIState() {

                //Hilo que esta pendiente de la vida de la VIEW, si la view muere el para!
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        <name>ViewModel.state.collect {
                            //Siempre que cambien el estado hara lo siguiente
                            when (it) {
                                //it es la informacion del estado que puede contener informacion.
                                //Cada cambio de estado llama a su metodo
                                <name>State.Loading -> loadingState()
                                is <name>State.Error -> errorState(it)
                                is <name>State.Success -> successState(it)
                            }
                        }
                    }
                }
            }

            private fun successState(it: <name>State.Success) {
                //Lo que quieras hacer cuando sea OK
            }

            private fun errorState(it: <name>State.Error) {
                //Lo que quieras hacer cuando sea Error
            }

            private fun loadingState() {
                //Lo que quieras hacer cuando esta cargando
            }


            SOLO QUEDARIA desde algun metodo o accion de un boton llamar a la funcion del ViewModel para que intente realizar la accion

            fun metodoRandom(){

                val user = "@usuario1"
                val password = "a722c63db8ec8625af6cf71cb8c2d939"

                <name>ViewModel.tryLogin(user,password)

            }


     */


}