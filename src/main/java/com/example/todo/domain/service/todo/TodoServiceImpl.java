package com.example.todo.domain.service.todo;

import java.util.Collection;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

import com.example.todo.domain.model.Todo;
import com.example.todo.domain.repository.todo.TodoRepository;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

	private static final long MAX_UNFINISHED_COUNT = 5;
	
	@Inject
	TodoRepository todoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Todo> findAll() {
		return todoRepository.finrdAll();
	}

	@Override
	public Todo create(Todo todo) {
		long unfinishedCount = todoRepository.countByFinished(false);
		if (unfinishedCount >= MAX_UNFINISHED_COUNT) {
			ResultMessages messages = ResultMessages.error();
			messages.add(ResultMessage.fromText("[E001] The count of un-finished Todo must not be over " + MAX_UNFINISHED_COUNT + "."));
			throw new BusinessException(messages);
		}
		
		String todoId = randomUUID().toString();
	}

	@Override
	public Todo finish(String todoId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void delete(String todoId) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
