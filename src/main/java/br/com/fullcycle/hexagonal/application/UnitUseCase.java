package br.com.fullcycle.hexagonal.application;

public abstract class UnitUseCase<INPUT> {
    // 1. Cada caso de uso tem um input e um Ouput próprio. Nào retorna a entidade , o agregadp, ou objeto de valor
    // 2. O caso de uso implementa o padrão de projeto Command

    public abstract void execute(INPUT input);
}
