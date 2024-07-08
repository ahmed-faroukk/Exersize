package com.farouk.exersize.features.home.domain.usecase

data class HomeUseCases (
    val getAllCoachesUseCase: GetAllCoachesUseCase,
     val getCoachUseCase: GetCoachUseCase,
     val getPackagesUseCase: GetPackagesUseCase,
     val reqPackageUseCase: ReqPackageUseCase,
    val getPortfolioUseCase: GetPortfolioUseCase
)
