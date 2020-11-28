package com.betbull.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betbull.player.model.PlayerRequest;
import com.betbull.player.model.DefaultPlayerResponse;
import com.betbull.player.model.MultiPlayerResponse;
import com.betbull.player.service.PlayerService;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@PostMapping("/create")
	@Transactional(propagation = Propagation.REQUIRED)
	public DefaultPlayerResponse createPlayer(@RequestBody PlayerRequest request) {
		return playerService.createPlayer(request);
	}
	
	@GetMapping("/getplayerbyname")
	public MultiPlayerResponse getPlayerByName(@RequestParam("name") String name) {
		return playerService.getPlayerByName(name);
	}
	
	@GetMapping("/getplayerbyid")
	public MultiPlayerResponse getPlayerById(@RequestParam("id") Long id) {
		return playerService.getPlayerById(id);
	}
	
	@GetMapping("/getallplayers")
	public MultiPlayerResponse getAllPlayers() {
		return playerService.getAllPlayers();
	}
	
	@PostMapping("/retire")
	public DefaultPlayerResponse retirePlayerById(@RequestParam Long id) {
		return playerService.retirePlayerById(id);
	}
	
	@DeleteMapping("/deleteplayerbyid")
	public DefaultPlayerResponse deletePlayerByName(@RequestParam("id") Long id){
		return playerService.deletePlayerById(id);
    }

	@GetMapping("/getactiveplayers")
	public MultiPlayerResponse getActivePlayers() {
		return playerService.getActivePlayers();
	}
	
	@PostMapping("/unassignplayerbyid")
	public DefaultPlayerResponse unAssignPlayerById(@RequestParam("id") Long id) {
		return playerService.unAssignPlayerById(id);
	}

	@GetMapping("/getplayerswithoutteam")
	public MultiPlayerResponse getFreePlayers() {
		return playerService.getPlayersWithoutTeam();
	}

	@GetMapping("/getretiredplayers")
	public MultiPlayerResponse getRetiredPlayers() {
		return playerService.getRetiredPlayers();
	}
	
	@DeleteMapping("/deleteall")
	public DefaultPlayerResponse deleteAll(){
		return playerService.deleteAll();
    }
}
