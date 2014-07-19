/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleproject;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ramesh
 */
@Entity
@Table(name = "word")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Word.findAll", query = "SELECT w FROM Word w"),
    @NamedQuery(name = "Word.findById", query = "SELECT w FROM Word w WHERE w.id = :id"),
    @NamedQuery(name = "Word.findByWord", query = "SELECT w FROM Word w WHERE w.word = :word"),
    @NamedQuery(name = "Word.findByCount", query = "SELECT w FROM Word w WHERE w.count = :count"),
    @NamedQuery(name = "Word.findByWeightage", query = "SELECT w FROM Word w WHERE w.weightage = :weightage")})
public class Word implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "word")
    private String word;
    @Basic(optional = false)
    @Column(name = "count")
    private int count;
    @Column(name = "weightage")
    private Integer weightage;

    public Word() {
    }

    public Word(Integer id) {
        this.id = id;
    }

    public Word(Integer id, String word, int count) {
        this.id = id;
        this.word = word;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getWeightage() {
        return weightage;
    }

    public void setWeightage(Integer weightage) {
        this.weightage = weightage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Word)) {
            return false;
        }
        Word other = (Word) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sampleproject.Word[ id=" + id + " ]";
    }
    
}
