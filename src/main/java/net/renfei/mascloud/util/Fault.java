package net.renfei.mascloud.util;

/**
 * com.ejtone.mars.kernel.core.fault.Fault
 *
 * @author renfei
 */
public class Fault extends Exception {
    private Err error;

    Fault(Err error) {
        super(error.toString());
        this.error = error;
    }

    Fault(Err error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public boolean becauseOf(String... reasons) {
        return this.error == null ? false : this.error.becauseOf(reasons);
    }

    public boolean becauseOf(Err... reasons) {
        return this.error == null ? false : this.error.becauseOf(reasons);
    }

    public boolean becauseOf(Class<?> clazz) {
        Throwable t = this.getCause();
        return t == null ? false : t.getClass().equals(clazz);
    }

    public Fault newInstance() {
        return new Fault(this.error);
    }

    public Fault newInstance(String message) {
        return new Fault(this.error.newInstance(message));
    }

    public Fault newInstance(Throwable cause) {
        return new Fault(this.error, cause);
    }

    public Err makeError() {
        return this.error;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("error:{").append(this.error.toString()).append("}");
        if (this.getCause() != null) {
            sb.append(",cause:{").append(this.getCause().toString()).append("}");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Fault f = Err.InternalError.makeFault(new Exception("aab"));
        System.out.println(f.toString());
        f = Err.InternalError.makeFault(new IllegalAccessError());
        System.out.println(f.becauseOf(IllegalAccessError.class));
    }
}
