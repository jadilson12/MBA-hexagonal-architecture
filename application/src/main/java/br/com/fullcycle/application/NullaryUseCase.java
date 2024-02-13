package br.com.fullcycle.application;

public abstract class NullaryUseCase<OUTPUT> {
    // 1. Cada caso de uso tem um input e um Ouput próprio. Nào retorna a entidade , o agregadp, ou objeto de valor
    // 2. O caso de uso implementa o padrão de projeto Command

    public abstract OUTPUT execute();

    public  <T> T execute(Presenter<OUTPUT, T> presenter) {
        try {
            return presenter.present(execute());
        } catch (Throwable t) {
            return presenter.present(t);
        }
    }
}
