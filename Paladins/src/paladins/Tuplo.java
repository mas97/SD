/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paladins;

/**
 *
 * @author MarcoSilva
 */
public class Tuplo<L,R> {

    private L l;
    private R r;

    public Tuplo(L l, R r) {
        this.l = l;
        this.r = r;
    }

    public L getL() {
        return l;
    }

    public R getR() {
        return r;
    }

    public void setL(L l) {
        this.l = l;
    }

    public void setR(R r) {
        this.r = r;
    }

    public boolean equals(Object o) {
		if (this == o) { return true; }
		if ( o == null || (this.getClass() != o.getClass()) )
			return false;
		Tuplo t = (Tuplo) o;
		return t.getL().equals(this.l) && t.getR().equals(this.r);
	}
}
