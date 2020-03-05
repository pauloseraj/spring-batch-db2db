package demo.controller;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLauncherController {
	
	@Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @RequestMapping("/jobLauncher")
    public String handle() throws Exception{
    	System.out.print("came");
    	JobParameters jobParameters = 
    			  new JobParametersBuilder()
    			  .addLong("time",System.currentTimeMillis()).toJobParameters();
    	JobExecution jb = jobLauncher.run(job, jobParameters);
    	if(jb.getStatus().equals(BatchStatus.FAILED)) {
    		return jb.getStepExecutions().toString();
    	}else {
    		return "Success";
    	}
    }
    
   

}
