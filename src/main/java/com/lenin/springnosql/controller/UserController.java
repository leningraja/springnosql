package com.lenin.springnosql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lenin.springnosql.model.User;
import com.lenin.springnosql.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public String getAll(Model model, @Param("keyword") String keyword) {
		try {
			List<User> users = new ArrayList<User>();
			log.info("Keyword for search: {}", keyword);
			if (keyword == null) {
				userService.findAll().forEach(users::add);
			} else {
				userService.getUserByName(keyword).forEach(users::add);
				model.addAttribute("keyword", keyword);
			}

			model.addAttribute("users", users);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "users";
	}
	
	@GetMapping("/usersPaging")
	public String getAll(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
		try {
			List<User> tutorials = new ArrayList<User>();
			Pageable paging = PageRequest.of(page - 1, size);

			Page<User> pageTuts;
			if (keyword == null) {
				pageTuts = userService.findAll(paging);
			} else {
				pageTuts = userService.findByNameContainingIgnoreCase(keyword, paging);
				model.addAttribute("keyword", keyword);
			}

			tutorials = pageTuts.getContent();

			model.addAttribute("users", tutorials);
			model.addAttribute("currentPage", pageTuts.getNumber() + 1);
			model.addAttribute("totalItems", pageTuts.getTotalElements());
			model.addAttribute("totalPages", pageTuts.getTotalPages());
			model.addAttribute("pageSize", size);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "users";
	}

	@GetMapping("/user/new")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("pageTitle", "Create new User");

		return "user_form";
	}

	@PostMapping("/user/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		try {
			log.info("************************************", user.isStatus());
			log.info("{}, {}", user.getId(), user.getUserId());
			userService.addNewUser(user);

			redirectAttributes.addFlashAttribute("message", "The user has been saved successfully!");
		} catch (Exception e) {
			log.error("Error while creating user. Exception: {}", e);
			redirectAttributes.addAttribute("message", e.getMessage());
		}

		return "redirect:/users";
	}

	@GetMapping("/user/edit/{id}")
	public String editUser(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
		try {
			Optional<User> user = userService.getUserById(id);

			model.addAttribute("user", user.isPresent() ? user.get() : new User());
			model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");

			return "user_form";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());

			return "redirect:/users";
		}
	}

	/*
	 * @GetMapping("/user/delete/{id}") public String deleteUser(@PathVariable("id")
	 * String id, Model model, RedirectAttributes redirectAttributes) { try {
	 * userService.deleteById(id);
	 * 
	 * redirectAttributes.addFlashAttribute("message", "The User with id=" + id +
	 * " has been deleted successfully!"); } catch (Exception e) {
	 * redirectAttributes.addFlashAttribute("message", e.getMessage()); }
	 * 
	 * return "redirect:/users"; }
	 */

	@GetMapping("/user/{id}/status/{status}")
	public String updateUserStatus(@PathVariable("id") String id, @PathVariable("status") boolean userStatus,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			User user = userService.updateUserStatus(id, userStatus);

			String message = "The Tutorial id=" + id + " has been " + user.isStatus();

			redirectAttributes.addFlashAttribute("message", message);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}

		return "redirect:/users";
	}
}
