package com.juaracoding.main.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.juaracoding.main.model.Worker;
import com.juaracoding.main.model.WorkerRowMapper;

@RestController
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	JdbcTemplate jdbc;
	
	public List<Worker> getWorker(){
		String sql = "Select * from worker";

		List<Worker> worker = jdbc.query(sql, new WorkerRowMapper());

		return worker;
	}
	
	@GetMapping("/")
	public List<Worker> getAll(){
		return getWorker();
	}
	
	public int insertWorker(Worker worker) {
		return jdbc.update("INSERT INTO worker(worker_id, first_name, last_name, salary, joining_date, department) VALUES ('"+ worker.getWorker_id() +"','"+ worker.getFirst_name() +"','"+ worker.getLast_name() 
		+"',"+ worker.getSalary() +",'"+ worker.getJoining_date() +"','"+ worker.getDepartment() +"')") ;

	}
	
	 @PostMapping("/")
	    public String add(@RequestBody Worker worker) {
		 

			if (this.insertWorker(worker) == 1) {
				return "Insert data berhasil";
			} else {
				return "Insert data gagal";
			}
	    }
	 
	 public int updateWorker(String worker_id, Worker worker) {

			return jdbc.update("UPDATE worker SET `first_name`='" + worker.getFirst_name() + "',`last_name`='" + worker.getLast_name()
					+ "',`id_salary`=" + worker.getSalary() + ",`joining_date`='" + worker.getJoining_date() + "',`department`='" + worker.getDepartment() + "' WHERE `worker_id` = '" + worker.getWorker_id() + "'");
			

		}
	 
	 @PutMapping("/{worker_id}")
	    public ResponseEntity<?> update(@RequestBody Worker worker, @PathVariable String worker_id) {
		 try {
	            updateWorker(worker_id, worker);
	            return new ResponseEntity<>(HttpStatus.OK);
	        } catch (NoSuchElementException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
		 
	 }
	 
	 public int deleteWorker(String worker_id) {
			return jdbc.update("DELETE FROM `worker` WHERE `worker_id` = '" + worker_id + "';");
		}
	 
	 @DeleteMapping("/{worker_id}")
	    public void delete(@PathVariable String worker_id) {
		 	deleteWorker(worker_id);
	 }
	 
	 	 /**********************/
	 	/* untuk soal nomor 2 */
	   /**********************/
	 
	 public List<Worker> getWorker2(){
			String sql = "Select * from worker order by salary desc limit 5";

			List<Worker> worker = jdbc.query(sql, new WorkerRowMapper());

			return worker;
		}
		
		@GetMapping("/nomor2")
		public List<Worker> List(){
			return getWorker2();
		}
		
		
		/**********************/
	   /* untuk soal nomor 3 */
	  /**********************/
		
	public List<Worker> getWorker3(){
			String sql = "SELECT * from worker where salary IN (SELECT salary from worker group by salary HAVING COUNT(*) > 1)";

			List<Worker> worker = jdbc.query(sql, new WorkerRowMapper());

			return worker;
		}
		
		@GetMapping("/nomor3")
		public List<Worker> List2(){
			return getWorker3();
		}
		
		
		  /**********************/
		 /* untuk soal nomor 4 */
		/**********************/
		
	public List<Worker> getWorker4(){
			String sql = "SELECT DISTINCT department, COUNT(*) as banyak from worker group by department";

			List<Worker> worker = jdbc.query(sql, new WorkerRowMapper());

			return worker;
		}
		
		@GetMapping("/nomor4")
		public List<Worker> List3(){
			return getWorker4();
		}
}
