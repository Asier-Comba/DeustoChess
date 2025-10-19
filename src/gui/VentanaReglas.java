package gui;

import java.awt.*;
import javax.swing.*;

public class VentanaReglas extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel lblReglas;
    private JScrollPane scroll;
    private JPanel pCentro, pSur;
    private JButton btnSalir;
    public VentanaReglas() {

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(400, 200, 700, 500); // un poco más grande
        setTitle("Reglas de DeustoChess");

        // === PANELES PRINCIPALES ===
        pSur = new JPanel();
        getContentPane().add(pSur, BorderLayout.SOUTH);
        
        pCentro = new JPanel(new BorderLayout());
        pCentro.setBackground(Color.WHITE);
        getContentPane().add(pCentro, BorderLayout.CENTER);

        
        btnSalir = new JButton("CERRAR PESTAÑA DE REGLAS");
        pSur.add(btnSalir);
        // === ETIQUETA CON TEXTO HTML ===
        lblReglas = new JLabel("<html><body style='width:650px; padding:10px; font-family:Arial; font-size:12px;'>"
        	    + "<h2>♟️ Reglas de DeustoChess</h2>"

        	    // === 0) VISIÓN GENERAL ===
        	    + "<h3>0) Visión general</h3>"
        	    + "<p>DeustoChess es una reinterpretación del ajedrez ambientada en la Universidad de Deusto. "
        	    + "Conserva la claridad espacial del tablero y el turno alterno, pero introduce piezas temáticas "
        	    + "(Rector, Bedel, Secretaria, Becario, Alumno y Máquina Expendedora) con habilidades únicas y "
        	    + "pequeños minijuegos que añaden humor, incertidumbre y decisiones de alto impacto.</p>"

        	    + "<h3>0.1 Propósito del juego</h3>"
        	    + "<ul>"
        	    + "<li><b>Educativo + creativo:</b> demostrar diseño y modelado OO en Java con un juego jugable y con identidad.</li>"
        	    + "<li><b>Accesible:</b> cualquiera que conozca ajedrez entiende lo básico en 1 minuto.</li>"
        	    + "<li><b>Expresivo:</b> cada pieza cuenta una historia del campus y habilita estilos de juego distintos.</li>"
        	    + "</ul>"

        	    + "<h3>0.2 Pilares de diseño</h3>"
        	    + "<ul>"
        	    + "<li><b>Reconocible:</b> tablero 8×8, movimientos clásicos como base.</li>"
        	    + "<li><b>Giro temático:</b> habilidades coherentes con la vida universitaria.</li>"
        	    + "<li><b>Impacto puntual:</b> poderes únicos por partida, decisiones memorables.</li>"
        	    + "<li><b>Azar con responsabilidad:</b> minijuegos que no sustituyen la estrategia.</li>"
        	    + "</ul>"

        	    + "<h3>0.3 Condición de victoria</h3>"
        	    + "<p>El objetivo es proteger al <b>Rector</b> y conseguir la <b>Expulsión del Rector enemigo</b> "
        	    + "(equivalente al jaque mate clásico).</p>"

        	    + "<h3>0.4 Roles de las piezas</h3>"
        	    + "<ul>"
        	    + "<li><b>Rector:</b> puede convocar una reunión de urgencia.</li>"
        	    + "<li><b>Bedel:</b> limpia una pieza enemiga visible sin moverse.</li>"
        	    + "<li><b>Secretaria:</b> reorganiza posiciones de piezas aliadas.</li>"
        	    + "<li><b>Becario:</b> hace dos movimientos consecutivos con posible evento de azar.</li>"
        	    + "<li><b>Alumno:</b> se gradúa al llegar al final del tablero.</li>"
        	    + "<li><b>Máquina Expendedora:</b> revitaliza piezas aliadas mediante minijuego.</li>"
        	    + "</ul>"

        	    + "<h3>0.5 Ritmo de partida</h3>"
        	    + "<p>El juego alterna turnos, con momentos de tensión al activar habilidades. "
        	    + "Los poderes deben usarse estratégicamente, ya que la mayoría solo pueden emplearse una vez por partida.</p>"

        	    // === 1) COMPONENTES ===
        	    + "<h2>1) Componentes</h2>"
        	    + "<h3>1.1 Visión general</h3>"
        	    + "<p>El universo de DeustoChess se desarrolla sobre un tablero de 8×8 casillas, donde cada jugador representa uno de los dos grandes bandos del campus universitario: <b>Blancos</b> y <b>Azules</b>.</p>"
        	    + "<p>Cada jugador dispone de un conjunto de 16 piezas, distribuidas según su rol en la jerarquía universitaria. "
        	    + "Cada pieza conserva el patrón de movimiento del ajedrez clásico, pero con una identidad propia y habilidades especiales que pueden alterar el curso de la partida.</p>"
        	    + "<p>Las piezas simbolizan diferentes estamentos y personajes de la Universidad de Deusto: "
        	    + "el <b>Rector</b> (máxima autoridad), el <b>Bedel</b> (orden y poder), las <b>Secretarias</b> (organización), los <b>Becarios</b> (agilidad y caos controlado), "
        	    + "las <b>Máquinas Expendedoras</b> (fuente de energía) y los <b>Alumnos</b> (base del sistema).</p>"

        	    + "<h3>1.2 Tablero</h3>"
        	    + "<p>El tablero tiene 8 filas y 8 columnas (64 casillas), alternadas entre colores claros y oscuros (blanco y azul).</p>"
        	    + "<p>Las coordenadas van del 0 al 7 tanto en filas como en columnas, siendo (0,0) la esquina inferior izquierda desde la perspectiva del jugador Blanco.</p>"
        	    + "<p>Se recomienda usar un marcador o sistema de coordenadas visible para facilitar la comunicación de jugadas.</p>"
        	    + "<p>No existe enroque ni casillas especiales: todas las mecánicas avanzadas provienen de las habilidades únicas de las piezas.</p>"

        	    + "<h3>1.3 Piezas por bando</h3>"
        	    + "<table border='1' cellspacing='0' cellpadding='4'>"
        	    + "<tr><th>Cantidad</th><th>Pieza</th><th>Rol tradicional</th><th>Función temática</th></tr>"
        	    + "<tr><td>1</td><td>Rector</td><td>Rey</td><td>Representa la dirección del campus. Si cae, el equipo pierde.</td></tr>"
        	    + "<tr><td>1</td><td>Bedel</td><td>Dama</td><td>Figura de máxima autoridad operativa. Tiene poder de limpieza a distancia.</td></tr>"
        	    + "<tr><td>2</td><td>Máquinas Expendedoras</td><td>Torres</td><td>Firmes y energéticas. Impulsan a las demás piezas.</td></tr>"
        	    + "<tr><td>2</td><td>Secretarias</td><td>Alfiles</td><td>Organización interna. Intercambian posiciones aliadas.</td></tr>"
        	    + "<tr><td>2</td><td>Becarios</td><td>Caballos</td><td>Ágiles y erráticos. Saltos imprevisibles y encadenados.</td></tr>"
        	    + "<tr><td>8</td><td>Alumnos</td><td>Peones</td><td>Base de la universidad; pueden avanzar, capturar y graduarse.</td></tr>"
        	    + "</table>"

        	    + "<h3>1.4 Colores o bandos</h3>"
        	    + "<p>Los bandos son <b>Blanco</b> y <b>Azul</b>. El jugador Blanco mueve primero.</p>"
        	    + "<p>Las piezas se distinguen por color, iconografía o emblema de facultad.</p>"
        	    + "<p>También pueden personalizarse por facultades (por ejemplo, <i>Informáticos vs Económicas</i>).</p>"

        	    + "<h3>1.5 Material adicional recomendado</h3>"
        	    + "<ul>"
        	    + "<li>Contador visual de habilidades usadas.</li>"
        	    + "<li>Indicadores de estado: Rector bajo Expediente, habilidades activas o agotadas.</li>"
        	    + "<li>Registro de eventos narrativos (p. ej. “El Bedel ha limpiado el pasillo”).</li>"
        	    + "</ul>"

        	    + "<h3>1.6 Resumen de equivalencias</h3>"
        	    + "<table border='1' cellspacing='0' cellpadding='4'>"
        	    + "<tr><th>Pieza clásica</th><th>Pieza Deusto</th><th>Habilidad principal</th></tr>"
        	    + "<tr><td>Rey</td><td>Rector</td><td>Reunión de urgencia (restringe el turno rival a Alumnos)</td></tr>"
        	    + "<tr><td>Dama</td><td>Bedel</td><td>Limpieza general (elimina una pieza enemiga sin moverse)</td></tr>"
        	    + "<tr><td>Torre</td><td>Máquina Expendedora</td><td>Café revitalizante (minijuego que da doble movimiento aliado)</td></tr>"
        	    + "<tr><td>Alfil</td><td>Secretaria</td><td>Reorganizar agenda (intercambia dos piezas aliadas)</td></tr>"
        	    + "<tr><td>Caballo</td><td>Becario</td><td>Corre que no llegas (cadena de saltos con azar decreciente)</td></tr>"
        	    + "<tr><td>Peón</td><td>Alumno</td><td>Graduación (al llegar al final se transforma en otra pieza)</td></tr>"
        	    + "</table>"

        	    // === 2) PREPARACIÓN ===
        	    + "<h2>2) Preparación</h2>"
        	    + "<h3>2.1 Tablero</h3>"
        	    + "<p>El tablero de DeustoChess es un cuadrado de 8×8 casillas, alternando colores claros (<b>blanco</b>) y oscuros (<b>azul</b>), igual que en el ajedrez clásico.</p>"
        	    + "<p>Las filas se numeran del 0 al 7 (de abajo arriba desde el punto de vista del jugador Blanco) y las columnas del 0 al 7 (de izquierda a derecha).</p>"
        	    + "<p>Cada jugador controla un conjunto de 16 piezas de su color:</p>"
        	    + "<ul><li><b>Blancos</b></li><li><b>Azules</b></li></ul>"
        	    + "<p>El objetivo sigue siendo expulsar al Rector rival.</p>"

        	    + "<h3>2.2 Colocación inicial de las piezas</h3>"
        	    + "<p>Desde la perspectiva del jugador Blanco (el que comienza la partida):</p>"
        	    + "<ul>"
        	    + "<li><b>Fila 7 (Azules):</b> Máquina – Becario – Secretaria – Bedel – Rector – Secretaria – Becario – Máquina</li>"
        	    + "<li><b>Fila 6 (Azules):</b> 8 Alumnos</li>"
        	    + "<li><b>Fila 1 (Blancos):</b> 8 Alumnos</li>"
        	    + "<li><b>Fila 0 (Blancos):</b> Máquina – Becario – Secretaria – Bedel – Rector – Secretaria – Becario – Máquina</li>"
        	    + "</ul>"
        	    + "<p>Cada pieza cumple el mismo rol posicional que su equivalente en el ajedrez clásico:</p>"
        	    + "<ul>"
        	    + "<li><b>Rector:</b> ocupa la casilla del Rey tradicional.</li>"
        	    + "<li><b>Bedel:</b> ocupa la casilla de la Dama.</li>"
        	    + "<li><b>Secretarias:</b> en lugar de los Alfiles.</li>"
        	    + "<li><b>Becarios:</b> en lugar de los Caballos.</li>"
        	    + "<li><b>Máquinas Expendedoras:</b> en lugar de las Torres.</li>"
        	    + "<li><b>Alumnos:</b> en lugar de los Peones.</li>"
        	    + "</ul>"

        	    + "<h3>2.3 Orden de juego</h3>"
        	    + "<p>Empieza siempre el bando <b>Blanco</b>.</p>"
        	    + "<p>Luego los turnos se alternan entre <b>Azul</b> y <b>Blanco</b> sucesivamente hasta que haya una "
        	    + "<b>Expulsión del Rector</b>, tablas o abandono.</p>"

        	    + "<h3>2.4 Preparación adicional (opcional)</h3>"
        	    + "<ul>"
        	    + "<li>Colocad un contador o marcador de habilidades usadas (por pieza o global).</li>"
        	    + "<li>Aseguraos de que ambos jugadores conocen las habilidades únicas de sus piezas.</li>"
        	    + "<li>Recordad las condiciones especiales de victoria: Expediente y Expulsión del Rector.</li>"
        	    + "<li>Si el juego usa cronómetro o límite de tiempo, ajustadlo antes de empezar.</li>"
        	    + "</ul>"

        	    + "<h3>2.5 Inicio de partida</h3>"
        	    + "<p><i>“Los pasillos del campus están despejados, el Rector toma su posición, "
        	    + "los Bedeles revisan los pasillos y los Alumnos esperan las primeras clases. "
        	    + "Comienza la partida. Mueven los Blancos.”</i></p>"

        	    // === 3) OBJETIVO DE LA PARTIDA ===
        	    + "<h2>3) Objetivo de la partida</h2>"
        	    + "<h3>3.1 Finalidad del juego</h3>"
        	    + "<p>El propósito de DeustoChess es claro: proteger a tu <b>Rector</b> mientras intentas conseguir la "
        	    + "<b>Expulsión del Rector rival</b>.</p>"
        	    + "<p>La partida se desarrolla como una serie de turnos alternos entre los bandos Blanco y Azul, donde los jugadores mueven sus piezas, "
        	    + "utilizan habilidades especiales y tratan de controlar el tablero. "
        	    + "El equilibrio entre estrategia clásica, gestión de habilidades y decisiones arriesgadas determinará el resultado final.</p>"

        	    + "<h3>3.2 Condición de victoria</h3>"
        	    + "<p>Un jugador gana la partida cuando consigue provocar la <b>Expulsión del Rector</b> del equipo contrario.</p>"
        	    + "<p>Esto ocurre cuando:</p>"
        	    + "<ul><li>El Rector enemigo está bajo amenaza directa (en <b>Expediente</b>), y</li>"
        	    + "<li>El jugador rival no dispone de ninguna jugada legal que elimine esa amenaza.</li></ul>"
        	    + "<p>En ese momento se declara: <b>“¡Expulsión del Rector!”</b> y la partida finaliza de inmediato.</p>"
        	    + "<p>La victoria representa la destitución del Rector enemigo, el cierre administrativo del campus rival y la hegemonía del jugador vencedor.</p>"

        	    + "<h3>3.3 Otras formas de victoria</h3>"
        	    + "<ul>"
        	    + "<li><b>🧩 Abandono:</b> un jugador puede rendirse en cualquier momento, declarando que entrega el campus.</li>"
        	    + "<li><b>⏱️ Tiempo agotado:</b> si se juega con cronómetro, el jugador que excede su tiempo reglamentario pierde automáticamente.</li>"
        	    + "<li><b>🧹 Inmovilización completa:</b> si, por efectos de habilidades o posiciones bloqueadas, el rival no puede mover su Rector y se encuentra en Expediente, se considera Expulsión.</li>"
        	    + "</ul>"

        	    + "<h3>3.4 Condiciones de empate (tablas)</h3>"
        	    + "<p>Una partida puede terminar en empate (o “tablas”) en los siguientes casos:</p>"
        	    + "<table border='1' cellspacing='0' cellpadding='4'>"
        	    + "<tr><th>Situación</th><th>Descripción</th></tr>"
        	    + "<tr><td>Ahogado (estancamiento)</td><td>El jugador al que le toca mover no está en Expediente, pero no tiene ningún movimiento legal.</td></tr>"
        	    + "<tr><td>Repetición triple</td><td>La misma posición se repite tres veces consecutivas con el mismo jugador al turno.</td></tr>"
        	    + "<tr><td>Regla de 50 movimientos</td><td>Han pasado 50 movimientos consecutivos sin capturas ni graduaciones.</td></tr>"
        	    + "<tr><td>Acuerdo mutuo</td><td>Ambos jugadores acuerdan el empate por decisión conjunta.</td></tr>"
        	    + "</table>"
        	    + "<p>En todas las situaciones de empate, el campus entra en un estado de equilibrio administrativo: "
        	    + "nadie gana, pero tampoco se declara una destitución.</p>"

        	    + "<h3>3.5 Condición de derrota</h3>"
        	    + "<p>Un jugador pierde automáticamente si:</p>"
        	    + "<ul>"
        	    + "<li>Su Rector es expulsado,</li>"
        	    + "<li>Abandona voluntariamente,</li>"
        	    + "<li>O no puede realizar ningún movimiento legal mientras su Rector está bajo Expediente.</li>"
        	    + "</ul>"
        	    + "<p>En caso de expulsión, se anuncia de forma oficial:</p>"
        	    + "<p><b>🧹 “¡Expulsión del Rector del bando Azul! Victoria para los Blancos.”</b><br>"
        	    + "<b>🧹 “¡Expulsión del Rector del bando Blanco! Victoria para los Azules.”</b></p>"
        	    +"<h3>3.6 Resumen conceptual</h3>"
        	    + "<table border='1' cellspacing='0' cellpadding='4'>"
        	    + "<tr><th>Término clásico</th><th>Término DeustoChess</th><th>Significado</th></tr>"
        	    + "<tr><td>Jaque</td><td>¡Expediente!</td><td>El Rector está bajo amenaza directa.</td></tr>"
        	    + "<tr><td>Jaque Mate</td><td>¡Expulsión del Rector!</td><td>El Rector está en amenaza sin escapatoria posible.</td></tr>"
        	    + "<tr><td>Tablas</td><td>Equilibrio administrativo</td><td>No hay posibilidad de victoria ni derrota.</td></tr>"
        	    + "</table>"

        	    + "<h3>3.7 Espíritu del juego</h3>"
        	    + "<p>DeustoChess no es solo una batalla de estrategia: "
        	    + "es una sátira universitaria y una simulación de poder institucional, donde la gestión de recursos (piezas), "
        	    + "la toma de decisiones y el momento en que se activan las habilidades determinan el destino del campus.</p>"
        	    + "<p>Cada partida cuenta una historia:</p>"
        	    + "<ul>"
        	    + "<li>Bedeles que imponen orden,</li>"
        	    + "<li>Becarios que improvisan milagros,</li>"
        	    + "<li>Secretarias que salvan al Rector en el último instante,</li>"
        	    + "<li>Y Alumnos que logran graduarse para cambiar el equilibrio del campus.</li>"
        	    + "</ul>"
        	    + "<p>La victoria pertenece a quien sabe cuándo actuar, cuándo esperar y cuándo limpiar el pasillo.</p>"
        		
				+ "<h2>4) Estructura del turno</h2>"
				+ "<h3>4.1 Orden general</h3>"
				+ "<p>El juego avanza en turnos alternos entre los dos bandos: <b>Blanco</b> mueve primero, luego <b>Azul</b>, y así sucesivamente. "
				+ "Cada turno representa una jornada dentro del campus y se divide en cuatro fases. "
				+ "Durante su turno, un jugador puede mover una sola pieza, a menos que una habilidad especial indique lo contrario.</p>"

				+ "<h3>4.2 Fases del turno</h3>"
				+ "<p><b>🔹 1. Fase de comprobación:</b> comprobar si el Rector está en Expediente y si existen efectos persistentes (como 'Reunión de urgencia').</p>"
				+ "<p><b>🔹 2. Fase de habilidad (opcional):</b> el jugador puede activar una habilidad si la pieza no la ha usado aún y el Rector no queda en peligro.</p>"
				+ "<p><b>🔹 3. Fase de movimiento:</b> mover una pieza siguiendo su patrón. Si captura, la pieza rival se retira. "
				+ "Durante esta fase pueden producirse graduaciones o efectos automáticos.</p>"
				+ "<p><b>🔹 4. Fase de cierre del turno:</b> comprobar si el rival está en Expediente o Expulsión, actualizar habilidades y pasar turno.</p>"

+ "<h3>4.3 Reglas generales del turno</h3>"
+ "<ul>"
+ "<li>Una acción de habilidad o un movimiento, pero no ambas con la misma pieza (salvo excepciones).</li>"
+ "<li>Solo un uso de habilidad por turno.</li>"
+ "<li>El Rector no puede quedar en Expediente al finalizar el turno propio.</li>"
+ "<li>No existe enroque.</li>"
+ "<li>Si un jugador no tiene movimientos legales y su Rector no está en Expediente → tablas.</li>"
+ "</ul>"

+ "<h3>4.4 Secuencia resumida</h3>"
+ "<ol>"
+ "<li>Comprobación</li>"
+ "<li>(Opcional) Activar habilidad</li>"
+ "<li>Mover pieza / resolver capturas</li>"
+ "<li>Comprobar estados y pasar turno</li>"
+ "</ol>"

+ "<h2>5) Movimiento de las piezas</h2>"
+ "<p>En DeustoChess cada pieza mantiene el patrón de movimiento clásico del ajedrez, "
+ "pero puede alterar el tablero mediante su habilidad única.</p>"

+ "<h3>5.1 👑 Rector</h3>"
+ "<p><b>Movimiento base:</b> una casilla en cualquier dirección. No puede entrar en casillas amenazadas. "
+ "<b>Objetivo:</b> sobrevivir. Si es capturado, se produce la Expulsión.</p>"
+ "<p><b>Habilidad – Reunión de urgencia (1 vez):</b> durante el siguiente turno rival, solo podrá mover Alumnos.</p>"

+ "<h3>5.2 👸 Bedel</h3>"
+ "<p><b>Movimiento base:</b> cualquier número de casillas en línea recta (vertical, horizontal o diagonal). "
+ "<b>Habilidad – Limpieza general (1 vez):</b> elimina una pieza enemiga visible en línea recta o diagonal sin moverse.</p>"

+ "<h3>5.3 🏪 Máquina Expendedora</h3>"
+ "<p><b>Movimiento base:</b> cualquier número de casillas en línea recta horizontal o vertical. "
+ "<b>Habilidad – Café revitalizante (ilimitada, con riesgo):</b> intenta un minijuego de tragaperras; "
+ "si acierta, concede un doble movimiento a una pieza aliada; si falla, pierde el turno.</p>"

+ "<h3>5.4 🧍‍♀️ Secretaria</h3>"
+ "<p><b>Movimiento base:</b> cualquier número de casillas en diagonal. "
+ "<b>Habilidad – Reorganizar agenda (1 vez):</b> intercambia las posiciones de dos piezas aliadas excepto Alumnos. "
+ "No afecta al Rector (recomendado para equilibrio).</p>"

+ "<h3>5.5 💻 Becario</h3>"
+ "<p><b>Movimiento base:</b> salto en L, como el Caballo. "
+ "<b>Habilidad – Corre que no llegas (1 vez):</b> realiza dos saltos consecutivos; si captura, puede encadenar "
+ "con probabilidades decrecientes (50%, 25%, 12.5%). Termina al fallar el azar o quedarse sin movimientos.</p>"

+ "<h3>5.6 🎓 Alumno</h3>"
+ "<p><b>Movimiento base:</b> avanza una casilla (dos si es su primer movimiento) y captura en diagonal. "
+ "<b>Habilidad pasiva – Graduación:</b> al llegar a la última fila se transforma en Bedel, Secretaria o Becario, "
+ "sin poder usar su habilidad ese mismo turno.</p>"

+ "<h3>5.7 Limitaciones comunes</h3>"
+ "<ul>"
+ "<li>Ninguna pieza puede saltar sobre otras salvo el Becario.</li>"
+ "<li>El movimiento debe mantener a la pieza dentro del tablero (0–7).</li>"
+ "<li>Si deja al propio Rector en Expediente, el movimiento es ilegal.</li>"
+ "<li>No existe enroque ni movimientos combinados salvo los de habilidades.</li>"
+ "</ul>"

+ "<h3>5.8 Resumen visual</h3>"
+ "<table border='1' cellspacing='0' cellpadding='4'>"
+ "<tr><th>Pieza</th><th>Patrón de movimiento</th><th>Tipo de habilidad</th></tr>"
+ "<tr><td>👑 Rector</td><td>1 casilla en cualquier dirección</td><td>Restringe el turno rival a Alumnos</td></tr>"
+ "<tr><td>👸 Bedel</td><td>Rectas y diagonales</td><td>Captura a distancia sin moverse</td></tr>"
+ "<tr><td>🏪 Máquina Expendedora</td><td>Rectas</td><td>Doble movimiento aliado con minijuego</td></tr>"
+ "<tr><td>🧍‍♀️ Secretaria</td><td>Diagonales</td><td>Intercambia dos piezas aliadas (excepto Alumnos)</td></tr>"
+ "<tr><td>💻 Becario</td><td>En L</td><td>Saltos consecutivos con azar decreciente</td></tr>"
+ "<tr><td>🎓 Alumno</td><td>Avanza 1 (2 en inicio), captura diagonal</td><td>Se gradúa al llegar al final</td></tr>"
+ "</table>"
+ "<h2>6) Estados especiales</h2>"
+ "<p>En DeustoChess existen dos estados críticos que determinan la seguridad del Rector: "
+ "<b>Expediente</b> y <b>Expulsión del Rector</b>. Ambos reemplazan los conceptos clásicos de Jaque y Jaque Mate, "
+ "pero con identidad y terminología propias del mundo universitario.</p>"

+ "<h3>6.1 Estado “¡Expediente!”</h3>"
+ "<p>El Expediente representa una situación de peligro directo para el Rector. "
+ "Ocurre cuando una o más piezas enemigas amenazan la casilla donde se encuentra el Rector.</p>"
+ "<p><b>Condiciones:</b> el Rector del jugador activo está bajo amenaza directa de captura y debe resolverlo en su siguiente turno.</p>"
+ "<p><b>Resolución:</b> durante su turno el jugador debe realizar una acción que elimine la amenaza: mover el Rector, capturar la pieza enemiga, "
+ "interponer una pieza aliada o usar una habilidad que deje al Rector a salvo. "
+ "Si al final del turno el Rector sigue en Expediente, la partida finaliza con su Expulsión inmediata.</p>"
+ "<p><b>Comunicación visual:</b> mostrar el texto “📄 ¡Expediente al Rector!” y resaltar al Rector afectado (por ejemplo, con un marco rojo o parpadeo).</p>"

+ "<h3>6.2 Estado “¡Expulsión del Rector!”</h3>"
+ "<p>La Expulsión del Rector equivale al jaque mate. Indica el final de la partida y la derrota total del jugador cuyo Rector ha sido expulsado del campus.</p>"
+ "<p><b>Condiciones:</b> el Rector está bajo amenaza directa (Expediente) y no existe ningún movimiento legal que elimine dicha amenaza.</p>"
+ "<p>El turno finaliza y se anuncia oficialmente:</p>"
+ "<p><b>🧹 “¡Expulsión del Rector del bando Azul! ¡Victoria de los Blancos!”</b><br>"
+ "<b>🧹 “¡Expulsión del Rector del bando Blanco! ¡Victoria de los Azules!”</b></p>"
+ "<p><b>Simbolismo:</b> representa la destitución del liderazgo universitario y el cierre del campus rival; "
+ "es el desenlace natural del conflicto jerárquico de DeustoChess.</p>"

+ "<h3>6.3 Casos relacionados</h3>"
+ "<ul>"
+ "<li><b>🟦 Ahogado (equilibrio administrativo):</b> sin movimientos legales pero sin Expediente → empate.</li>"
+ "<li><b>📘 “El campus entra en equilibrio: no hay despidos ni victorias.”</b></li>"
+ "<li><b>🔄 Repetición triple:</b> misma posición tres veces con el mismo jugador al turno → tablas.</li>"
+ "<li><b>⏱️ Regla de 50 movimientos:</b> 50 movimientos sin capturas ni graduaciones → empate administrativo.</li>"
+ "<li><b>🤝 Acuerdo mutuo:</b> los jugadores pueden pactar tablas en cualquier momento (Paz Universitaria).</li>"
+ "</ul>"

+ "<h3>6.4 Interacciones con habilidades</h3>"
+ "<ul>"
+ "<li>Ninguna habilidad puede anular un Expediente si el Rector sigue amenazado tras su resolución.</li>"
+ "<li>Las habilidades con azar (como la Máquina Expendedora) no pueden usarse si el jugador está en Expediente sin salida segura.</li>"
+ "<li>El Rector no puede usar su habilidad si ya está en Expediente y esta no garantiza su seguridad al final del turno.</li>"
+ "</ul>"

+ "<h2>7) Habilidades oficiales</h2>"
+ "<p>Cada pieza posee una habilidad única inspirada en su rol universitario. "
+ "Las habilidades aportan estrategia y humor, pero su uso debe ser táctico, ya que la mayoría solo pueden activarse una vez por partida.</p>"

+ "<h3>7.1 👑 Rector — “Reunión de urgencia”</h3>"
+ "<p><b>Usos:</b> 1 por partida.<br>"
+ "<b>Activación:</b> durante el turno propio, si el Rector no está en Expediente.<br>"
+ "<b>Efecto:</b> en el siguiente turno rival, solo podrá mover Alumnos. "
+ "Si no tiene movimientos legales con Alumnos, pierde el turno completo.<br>"
+ "<b>Duración:</b> 1 turno rival.<br>"
+ "<b>Narrativa:</b> representa una reunión administrativa que paraliza el campus.</p>"
+ "<p>🗣️ “El Rector ha convocado una reunión de urgencia. Solo los Alumnos podrán moverse durante el siguiente turno.”</p>"

+ "<h3>7.2 👸 Bedel — “Limpieza general”</h3>"
+ "<p><b>Usos:</b> 1 por partida.<br>"
+ "<b>Activación:</b> durante el turno propio.<br>"
+ "<b>Efecto:</b> elimina una pieza enemiga visible en línea recta o diagonal sin moverse, "
+ "siempre que tenga línea de visión directa. No puede atravesar piezas.<br>"
+ "<b>Restricción:</b> no puede dejar al Rector en Expediente.<br>"
+ "<b>Narrativa:</b> simboliza la autoridad del Bedel para imponer orden en los pasillos.</p>"
+ "<p>🧹 “El Bedel realiza una limpieza general. La pieza enemiga desaparece del tablero.”</p>"

+ "<h3>7.3 🏪 Máquina Expendedora — “Café revitalizante (Tragaperras)”</h3>"
+ "<p><b>Usos:</b> ilimitados.<br>"
+ "<b>Activación:</b> durante el turno propio, en lugar de mover una pieza.<br>"
+ "<b>Efecto:</b> lanza un minijuego tipo tragaperras. Si obtiene tres símbolos iguales, "
+ "una pieza aliada de la misma fila o columna obtiene un doble movimiento inmediato. "
+ "Si falla, pierde el turno.<br>"
+ "<b>Restricciones:</b> no puede usarse si el jugador está en Expediente ni otorgar doble movimiento al Rector.<br>"
+ "<b>Narrativa:</b> representa la “inyección de cafeína” que impulsa la productividad del campus.</p>"
+ "<p>☕ “La Máquina Expendedora ha entregado tres cafés perfectos. Una pieza aliada podrá moverse dos veces.”</p>"

+ "<h3>7.4 🧍‍♀️ Secretaria — “Reorganizar agenda”</h3>"
+ "<p><b>Usos:</b> 1 por partida.<br>"
+ "<b>Activación:</b> durante el turno propio.<br>"
+ "<b>Efecto:</b> intercambia las posiciones de dos piezas aliadas excepto Alumnos. No afecta al Rector.<br>"
+ "<b>Restricción:</b> no puede dejar al Rector en Expediente.<br>"
+ "<b>Narrativa:</b> simboliza la habilidad para reorganizar tareas y mover 'papeles' (o piezas) a conveniencia.</p>"
+ "<p>📅 “La Secretaria reorganiza la agenda. Dos piezas aliadas intercambian posiciones.”</p>"

+ "<h3>7.5 💻 Becario — “Corre que no llegas”</h3>"
+ "<p><b>Usos:</b> 1 por partida.<br>"
+ "<b>Activación:</b> durante el turno propio.<br>"
+ "<b>Efecto:</b> realiza dos saltos consecutivos. "
+ "Si captura, puede continuar con probabilidades decrecientes (50%, 25%, 12.5%). "
+ "Termina al fallar el azar o quedarse sin movimientos.<br>"
+ "<b>Narrativa:</b> refleja la energía imprevisible del Becario, que corre de aula en aula.</p>"
+ "<p>💻 “El Becario salta de clase en clase. ¡Podría seguir… o colapsar!”</p>"

+ "<h3>7.6 🎓 Alumno — “Graduación”</h3>"
+ "<p><b>Usos:</b> pasivo.<br>"
+ "<b>Condición:</b> llegar a la última fila del rival.<br>"
+ "<b>Efecto:</b> se transforma en Bedel, Secretaria o Becario (a elección del jugador). "
+ "La nueva pieza no puede usar su habilidad en ese mismo turno.<br>"
+ "<b>Narrativa:</b> representa la culminación académica y el ascenso jerárquico dentro del campus.</p>"
+ "<p>🎓 “¡El Alumno se ha graduado! Asciende a Bedel, Secretaria o Becario.”</p>"

+ "<h3>7.7 Normas generales de habilidades</h3>"
+ "<ul>"
+ "<li>Máximo una habilidad por turno.</li>"
+ "<li>Las habilidades no pueden dejar al propio Rector en Expediente.</li>"
+ "<li>Si una habilidad falla, el turno se pierde.</li>"
+ "<li>Las habilidades no se acumulan (no hay dobles efectos).</li>"
+ "<li>Efectos globales (como la restricción del Rector) expiran al final del siguiente turno rival.</li>"
+ "<li>Se recomienda mostrar qué piezas ya han usado su habilidad para mayor claridad visual.</li>"
+ "</ul>"
+ "<h2>8) Interacciones y prioridades</h2>"
+ "<p>En DeustoChess, varias habilidades pueden coincidir durante la partida. "
+ "Para mantener la claridad, todas las interacciones siguen un orden de prioridad que garantiza resoluciones coherentes.</p>"

+ "<h3>8.1 Orden de prioridad de los efectos</h3>"
+ "<table border='1' cellspacing='0' cellpadding='4'>"
+ "<tr><th>Prioridad</th><th>Tipo de efecto</th><th>Descripción</th></tr>"
+ "<tr><td>1️⃣</td><td>Supervivencia del Rector</td><td>Cualquier efecto que determine la seguridad del Rector tiene prioridad absoluta.</td></tr>"
+ "<tr><td>2️⃣</td><td>Restricciones globales</td><td>Efectos que afectan al turno rival (p. ej., Reunión de urgencia).</td></tr>"
+ "<tr><td>3️⃣</td><td>Resoluciones directas</td><td>Acciones inmediatas como Limpieza general o Reorganizar agenda.</td></tr>"
+ "<tr><td>4️⃣</td><td>Efectos de azar</td><td>Minijuegos como la Máquina Expendedora o la cadena del Becario.</td></tr>"
+ "<tr><td>5️⃣</td><td>Acciones reactivas o pasivas</td><td>Graduación del Alumno o efectos automáticos.</td></tr>"
+ "</table>"

+ "<h3>8.2 Restricción del Rector: “solo Alumnos”</h3>"
+ "<p>Cuando el Rector activa Reunión de urgencia, durante el siguiente turno rival solo pueden moverse piezas tipo Alumno. "
+ "Si no hay movimientos legales de Alumnos, el turno se pierde. Este efecto expira al final del turno rival y no puede ser extendido.</p>"
+ "<p>🗣️ “El campus entero está reunido. Solo los Alumnos se mueven entre los pasillos.”</p>"

+ "<h3>8.3 Habilidad del Bedel y el Rector</h3>"
+ "<p>Si el Bedel elimina una pieza y deja al Rector rival en Expediente, el efecto es válido. "
+ "Si deja a su propio Rector en Expediente, la acción es ilegal y no se ejecuta.</p>"

+ "<h3>8.4 Interacciones con la Máquina Expendedora</h3>"
+ "<ul>"
+ "<li>Si la tragaperras acierta (tres símbolos iguales), el efecto se aplica de inmediato y la pieza elegida no puede recibir otro igual ese turno.</li>"
+ "<li>Si falla, el jugador pierde el turno completo.</li>"
+ "<li>Si el bando está en Expediente sin salida garantizada, el intento no está permitido.</li>"
+ "</ul>"
+ "<p>☕ “Falló el café. El campus entra en modo siesta.”</p>"

+ "<h3>8.5 Secretaria y combinaciones tácticas</h3>"
+ "<p>La Secretaria puede intercambiar piezas aliadas aunque una haya usado su habilidad, pero no puede:</p>"
+ "<ul>"
+ "<li>Intercambiar Alumnos,</li>"
+ "<li>Mover o salvar al Rector,</li>"
+ "<li>Generar posiciones que dejen al Rector en Expediente.</li>"
+ "</ul>"
+ "<p>El intercambio es instantáneo y no genera amenazas ni capturas intermedias.</p>"
+ "<p>📅 “Dos despachos cambian de lugar, pero la burocracia sigue intacta.”</p>"

+ "<h3>8.6 Becario y Máquina Expendedora</h3>"
+ "<p>Si el Becario recibe doble movimiento por la Máquina Expendedora, puede usar su habilidad <i>Corre que no llegas</i> ese turno.</p>"
+ "<ul>"
+ "<li>Si la tragaperras falla → se pierde el turno antes de usar la habilidad.</li>"
+ "<li>Si acierta → el Becario ejecuta sus saltos consecutivos como parte del doble movimiento.</li>"
+ "</ul>"
+ "<p>Esto permite combinaciones poderosas pero arriesgadas si el azar no acompaña.</p>"

+ "<h3>8.7 Graduación y efectos activos</h3>"
+ "<p>Cuando un Alumno se gradúa, se eliminan todos los efectos activos sobre él (doble movimiento, intercambio, etc.). "
+ "La nueva pieza entra con su habilidad bloqueada un turno. Si la graduación provoca un Expediente rival, se considera válida.</p>"

+ "<h3>8.8 Interacciones prohibidas</h3>"
+ "<ul>"
+ "<li>Usar Limpieza general y Reorganizar agenda en el mismo turno.</li>"
+ "<li>Usar la habilidad del Rector junto con otra habilidad en el mismo turno.</li>"
+ "<li>Usar una habilidad después de mover (salvo efectos automáticos).</li>"
+ "<li>Repetir resultados de azar (tragaperras o Becario) mediante habilidades.</li>"
+ "</ul>"

+ "<h3>8.9 Principio general de resolución</h3>"
+ "<p><b>“En caso de duda, prima la supervivencia del Rector.”</b><br>"
+ "Si dos efectos son simultáneos o contradictorios, prevalece el que mantenga al Rector con vida. "
+ "En conflictos no previstos, los jugadores pueden consultar un árbitro o acordar una resolución consensuada.</p>"

+ "<h2>9) Situaciones límite</h2>"
+ "<p>Estas reglas cubren casos excepcionales o raros que pueden surgir durante una partida.</p>"

+ "<h3>9.1 Sin movimientos legales</h3>"
+ "<p>Si un jugador no puede mover ninguna pieza pero su Rector no está en Expediente → tablas. "
+ "Si está en Expediente → Expulsión del Rector (derrota).</p>"

+ "<h3>9.2 Múltiples efectos de azar simultáneos</h3>"
+ "<p>Si dos efectos aleatorios deben resolverse a la vez, se resuelven en orden de activación. "
+ "Cada evento de azar es independiente y no modifica los resultados de los demás.</p>"

+ "<h3>9.3 Habilidades sin efecto</h3>"
+ "<p>Si una habilidad se activa pero no produce efecto (por ejemplo, sin objetivo válido), el turno se considera gastado igualmente.</p>"
+ "<p><i>“El tiempo administrativo ha sido malgastado. El turno concluye sin resultados.”</i></p>"

+ "<h3>9.4 Efectos persistentes mal gestionados</h3>"
+ "<p>Si un jugador olvida aplicar un efecto activo, este se considera caducado al final del turno. "
+ "No se retrocede la partida.</p>"

+ "<h3>9.5 Movimiento ilegal descubierto tarde</h3>"
+ "<p>Si se detecta un movimiento ilegal varios turnos después, la partida continúa. "
+ "Solo se repite si el error causó una Expulsión del Rector, por acuerdo de ambos jugadores.</p>"

+ "<h3>9.6 Desconexiones o interrupciones (modo digital)</h3>"
+ "<ul>"
+ "<li>Si un jugador se desconecta antes de finalizar su turno → abandono tras 2 minutos.</li>"
+ "<li>Si ocurre un error del sistema → restaurar la última posición válida.</li>"
+ "</ul>"

+ "<h3>9.7 Piezas bloqueadas</h3>"
+ "<p>Una pieza rodeada sin movimientos legales permanece en el tablero pero no puede usar su habilidad hasta liberarse.</p>"

+ "<h3>9.8 Habilidades encadenadas en empate</h3>"
+ "<p>Si una habilidad provoca simultáneamente un Expediente rival y un ahogado propio, "
+ "se prioriza la Expulsión del Rector enemigo → victoria del jugador que atacó.</p>"

+ "<h3>9.9 Fin de partida por error técnico o mutuo acuerdo</h3>"
+ "<p>Si ambos jugadores acuerdan que la partida está bloqueada o rota, pueden declarar conjuntamente:</p>"
+ "<p>🕊️ “Fin administrativo del curso. El campus queda en equilibrio.”</p>"

+ "<h3>9.10 Principio de deportividad</h3>"
+ "<p>DeustoChess valora la creatividad y el humor tanto como la estrategia. "
+ "Cualquier conducta antideportiva o abuso intencionado puede sancionarse con la retirada del Rector (derrota automática).</p>"
+ "<p>🎓 “Juega limpio, que el Bedel observa.”</p>"
+ "<h2>10) Condiciones de victoria y final de partida</h2>"
+ "<p>En DeustoChess, una partida concluye cuando se produce una <b>Expulsión del Rector</b>, "
+ "se alcanza un <b>empate administrativo</b>, o se da una finalización extraordinaria (abandono, tiempo o acuerdo). "
+ "Todas las condiciones están diseñadas para preservar la claridad y el equilibrio del juego.</p>"

+ "<h3>10.1 Expulsión del Rector (Victoria total)</h3>"
+ "<p>La partida termina inmediatamente cuando un jugador logra colocar al Rector enemigo en una situación de "
+ "amenaza directa (<b>Expediente</b>) sin movimientos legales posibles que eliminen dicha amenaza. "
+ "Esto constituye una Expulsión del Rector y otorga la victoria total al jugador atacante.</p>"
+ "<p>🧹 “¡Expulsión del Rector del bando Azul! El campus queda bajo nuevo rectorado.”</p>"
+ "<p><b>Registro oficial:</b><br>"
+ "Resultado: 1 - 0 (si gana el bando Blanco)<br>"
+ "Resultado: 0 - 1 (si gana el bando Azul)</p>"

+ "<h3>10.2 Empate administrativo (Tablas)</h3>"
+ "<p>Una partida puede finalizar en empate en los siguientes casos:</p>"
+ "<table border='1' cellspacing='0' cellpadding='4'>"
+ "<tr><th>Tipo de empate</th><th>Descripción</th><th>Resultado</th></tr>"
+ "<tr><td>Ahogado</td><td>El jugador que debe mover no tiene movimientos legales y su Rector no está en Expediente.</td><td>½ – ½</td></tr>"
+ "<tr><td>Repetición triple</td><td>La misma posición se repite tres veces consecutivas con el mismo jugador al turno.</td><td>½ – ½</td></tr>"
+ "<tr><td>Regla de 50 movimientos</td><td>Cincuenta movimientos consecutivos sin capturas ni graduaciones.</td><td>½ – ½</td></tr>"
+ "<tr><td>Acuerdo mutuo</td><td>Ambos jugadores acuerdan finalizar la partida en equilibrio.</td><td>½ – ½</td></tr>"
+ "</table>"
+ "<p>📘 “El campus entra en equilibrio administrativo. No hay vencedores ni vencidos.”</p>"

+ "<h3>10.3 Abandono</h3>"
+ "<p>Un jugador puede rendirse en cualquier momento declarando:<br>"
+ "🕊️ “El Rector presenta su dimisión.”</p>"
+ "<p>Esto se considera derrota inmediata y concede la victoria al rival. "
+ "Debe declararse claramente antes del siguiente movimiento del oponente.</p>"

+ "<h3>10.4 Derrota por tiempo</h3>"
+ "<p>Si la partida se juega con cronómetro y el tiempo de un jugador llega a cero, pierde automáticamente, "
+ "siempre que el rival tenga piezas suficientes para lograr la Expulsión del Rector.</p>"
+ "<p>⏱️ “El reloj académico ha vencido al jugador Azul. Victoria para los Blancos.”</p>"

+ "<h3>10.5 Fin por error o interrupción técnica</h3>"
+ "<p>En partidas digitales:</p>"
+ "<ul>"
+ "<li>Si el juego se interrumpe por fallo del sistema, debe restaurarse la última posición válida.</li>"
+ "<li>Si un jugador se desconecta por más de 2 minutos durante su turno, se considera abandono técnico.</li>"
+ "</ul>"

+ "<h3>10.6 Condiciones extraordinarias</h3>"
+ "<p>En torneos o partidas oficiales, un árbitro puede declarar el final por conducta antideportiva, "
+ "uso indebido de errores o infracciones graves.</p>"
+ "<p>🧾 “El Rector ha sido cesado por mala praxis.”</p>"

+ "<h3>10.7 Cierre narrativo</h3>"
+ "<p>Cada partida de DeustoChess cuenta una historia: el caos de los pasillos, la calma del Rectorado, "
+ "el poder del Bedel o la heroicidad de un Alumno que se gradúa en el último momento.</p>"
+ "<p>La victoria representa el ascenso al rectorado; el empate, la burocracia eterna; y la derrota, "
+ "el inevitable relevo del liderazgo.</p>"
+ "<p>🎓 “El curso ha terminado. El campus descansa… hasta la próxima partida.”</p>"

+ "<h2>11) Terminología oficial del juego</h2>"
+ "<p>Este glosario define los términos oficiales utilizados en DeustoChess, tanto en el tablero como en la interfaz, "
+ "reemplazando los conceptos clásicos del ajedrez por su versión universitaria.</p>"

+ "<h3>11.1 Términos generales</h3>"
+ "<table border='1' cellspacing='0' cellpadding='4'>"
+ "<tr><th>Término DeustoChess</th><th>Equivalente clásico</th><th>Descripción</th></tr>"
+ "<tr><td>Campus</td><td>Tablero</td><td>El escenario de 8×8 donde se desarrolla la partida.</td></tr>"
+ "<tr><td>Bando</td><td>Color</td><td>Grupo de piezas controladas por un jugador (Blancos o Azules).</td></tr>"
+ "<tr><td>Rector</td><td>Rey</td><td>Pieza principal. Si es expulsado, la partida termina.</td></tr>"
+ "<tr><td>Expediente</td><td>Jaque</td><td>Estado de peligro directo para el Rector.</td></tr>"
+ "<tr><td>Expulsión del Rector</td><td>Jaque Mate</td><td>Derrota definitiva cuando el Rector está sin salida.</td></tr>"
+ "<tr><td>Equilibrio administrativo</td><td>Tablas</td><td>Resultado empatado de la partida.</td></tr>"
+ "<tr><td>Graduación</td><td>Promoción</td><td>Transformación del Alumno al llegar a la última fila.</td></tr>"
+ "<tr><td>Reunión de urgencia</td><td>—</td><td>Habilidad del Rector que restringe el turno rival.</td></tr>"
+ "<tr><td>Limpieza general</td><td>—</td><td>Habilidad del Bedel para eliminar piezas enemigas a distancia.</td></tr>"
+ "<tr><td>Reorganizar agenda</td><td>—</td><td>Habilidad de la Secretaria para intercambiar piezas aliadas.</td></tr>"
+ "<tr><td>Café revitalizante</td><td>—</td><td>Habilidad de la Máquina Expendedora con minijuego de azar.</td></tr>"
+ "<tr><td>Corre que no llegas</td><td>—</td><td>Habilidad del Becario basada en saltos consecutivos.</td></tr>"
+ "<tr><td>Turno restringido a Alumnos</td><td>—</td><td>Efecto temporal causado por el Rector.</td></tr>"
+ "</table>"

+ "<h3>11.2 Frases oficiales del juego</h3>"
+ "<table border='1' cellspacing='0' cellpadding='4'>"
+ "<tr><th>Evento</th><th>Mensaje oficial</th></tr>"
+ "<tr><td>Rector bajo amenaza</td><td>📄 ¡Expediente al Rector del bando [color]!</td></tr>"
+ "<tr><td>Expulsión (fin de partida)</td><td>🧹 ¡Expulsión del Rector! ¡Victoria para el bando [color]!</td></tr>"
+ "<tr><td>Activación del Rector</td><td>📢 El Rector ha convocado una reunión de urgencia.</td></tr>"
+ "<tr><td>Activación del Bedel</td><td>🧹 El Bedel realiza una limpieza general.</td></tr>"
+ "<tr><td>Activación de la Secretaria</td><td>📅 La Secretaria reorganiza la agenda del campus.</td></tr>"
+ "<tr><td>Activación de la Máquina Expendedora</td><td>☕ La Máquina Expendedora lanza la tragaperras...</td></tr>"
+ "<tr><td>Fallo de la Máquina</td><td>💤 El café no surtió efecto. Turno perdido.</td></tr>"
+ "<tr><td>Activación del Becario</td><td>💻 El Becario corre que no llega…</td></tr>"
+ "<tr><td>Graduación de un Alumno</td><td>🎓 ¡El Alumno se gradúa! Asciende a [nueva pieza].</td></tr>"
+ "<tr><td>Empate</td><td>📘 El campus entra en equilibrio administrativo.</td></tr>"
+ "<tr><td>Abandono</td><td>🕊️ El Rector presenta su dimisión.</td></tr>"
+ "</table>"
+ "</body></html>");


        		
        btnSalir.addActionListener(e -> setVisible(false));
       

        // === SCROLL ===
        scroll = new JScrollPane(lblReglas);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16); // desplazamiento más fluido

        // === AÑADIR COMPONENTES ===
        pCentro.add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }

 
}

