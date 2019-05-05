package com.logmein.pokergame.service.pokergame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.logmein.pokergame.service.pokergame.models.Deck;
import com.logmein.pokergame.service.pokergame.services.DeckService;

@Controller
@RequestMapping(path = "/deck")
public class DeckController {
	private final DeckService deckService;
	
	@Autowired
	public DeckController(DeckService deckService) {
		this.deckService = deckService;
	}
	
	@PostMapping
	public @ResponseBody Deck createDeck() {
		return deckService.create();
	}
	
	@PutMapping
	public @ResponseBody Deck shuffleDeck(@RequestParam("deckId") String deckId) {
		return deckService.shuffle(deckId);
	}
	
}
