package cl.EducarParaTodos.modelo;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String tema;
    private String nivel; 
    private int popularidad; 

    @ManyToMany(mappedBy = "cursosInscritos")
    private List<Usuario> estudiantes;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leccion> lecciones;

    public Curso() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public int getPopularidad() { return popularidad; }
    public void setPopularidad(int popularidad) { this.popularidad = popularidad; }
    public List<Usuario> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(List<Usuario> estudiantes) { this.estudiantes = estudiantes; }
    public List<Leccion> getLecciones() { return lecciones; }
    public void setLecciones(List<Leccion> lecciones) { this.lecciones = lecciones; }
}