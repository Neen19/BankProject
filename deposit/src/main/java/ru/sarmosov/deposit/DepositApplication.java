package ru.sarmosov.deposit;


import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.sarmosov.deposit.factory.DepositFactory;
import ru.sarmosov.deposit.handler.RequestHandler;
import ru.sarmosov.deposit.repository.*;
import ru.sarmosov.deposit.service.deposit.DepositPercentService;
import ru.sarmosov.deposit.service.mail.MailService;
import ru.sarmosov.deposit.service.request.RequestServiceImpl;

@SpringBootApplication
@EnableScheduling
public class DepositApplication {

	private final DepositRepository depositRepository;
	private final PercentPaymentPeriodRepository percentPaymentPeriodRepository;
	private final DepositTypeRepository depositTypeRepository;
	private final DepositFactory factory;
	private final RequestStatusRepository requestStatusRepository;
	private final RequestRepository requestRepository;
	private final RequestServiceImpl service;
	private final RequestHandler requestHandler;
	private final MailService mailService;
	private final DepositPercentService percentService;

	public DepositApplication(DepositRepository depositRepository, PercentPaymentPeriodRepository percentPaymentPeriodRepository, DepositTypeRepository depositTypeRepository, DepositFactory factory, RequestStatusRepository requestStatusRepository, RequestRepository requestRepository, RequestServiceImpl service, RequestHandler requestHandler, MailService mailService, DepositPercentService percentService) {
		this.depositRepository = depositRepository;
		this.percentPaymentPeriodRepository = percentPaymentPeriodRepository;
		this.depositTypeRepository = depositTypeRepository;
		this.factory = factory;
		this.requestStatusRepository = requestStatusRepository;
		this.requestRepository = requestRepository;
		this.service = service;
		this.requestHandler = requestHandler;
		this.mailService = mailService;
		this.percentService = percentService;
	}


//	@PostConstruct
//	public void init() {
//		requestStatusRepository.save(new RequestStatusEntity(RequestStatus.PENDING_CONFIRMATION));
//		requestStatusRepository.save(new RequestStatusEntity(RequestStatus.CONFIRMED));
//		requestStatusRepository.save(new RequestStatusEntity(RequestStatus.REJECTED));
//		requestStatusRepository.save(new RequestStatusEntity(RequestStatus.APPROVED));
//
//		depositTypeRepository.save(new DepositTypeEntity(DepositType.WITH_DEPOSIT_AND_WITHDRAWAL));
//		depositTypeRepository.save(new DepositTypeEntity(DepositType.WITH_DEPOSIT_NO_WITHDRAWAL));
//		depositTypeRepository.save(new DepositTypeEntity(DepositType.NO_DEPOSIT_NO_WITHDRAWAL));
//
//		percentPaymentPeriodRepository.save(new PercentPaymentPeriodEntity(PercentPaymentPeriod.THREE_MONTHS));
//		percentPaymentPeriodRepository.save(new PercentPaymentPeriodEntity(PercentPaymentPeriod.SIX_MONTHS));
//		percentPaymentPeriodRepository.save(new PercentPaymentPeriodEntity(PercentPaymentPeriod.ONE_YEAR));
//	}

	@PostConstruct
	public void init() {
		percentService.payPercent();
	}

	public static void main(String[] args) {
		SpringApplication.run(DepositApplication.class, args);
	}
}

