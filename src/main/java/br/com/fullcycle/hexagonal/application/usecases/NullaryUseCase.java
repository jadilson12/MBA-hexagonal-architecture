package br.com.fullcycle.hexagonal.application.usecases;

public abstract class NullaryUseCase<OUTPUT> {
    // 1. Cada caso de uso tem um input e um Ouput próprio. Nào retorna a entidade , o agregadp, ou objeto de valor
    // 2. O caso de uso implementa o padrão de projeto Command

    public abstract OUTPUT execute();
}
