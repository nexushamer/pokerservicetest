package com.logmein.pokergame.service.pokergame.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.logmein.pokergame.service.pokergame.models.Card;
import com.logmein.pokergame.service.pokergame.models.Player;
import com.logmein.pokergame.service.pokergame.services.PlayerServices;

@Controller
@RequestMapping(path = "/player")
public class PlayerController {
	private final PlayerServices playerService;
	
	@Autowired
	public PlayerController(PlayerServices playerService) {
		this.playerService = playerService;
	}
	
	@PostMapping
	public @ResponseBody Player createPlayer(@RequestBody Player player) {
		return playerService.createPlayer(player.getEmail());
	}
	
	@GetMapping(path = "/listCards")
	public @ResponseBody List<Card> listOfCardsFromPlayer(@RequestParam("email") String email) {
		return playerService.listOfCardsFromPlayer(email);
	}
	
}
