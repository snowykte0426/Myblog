let posts = [];
let uniqueTags = [];

document.addEventListener('DOMContentLoaded', () => {
    const postList = document.getElementById('postList');
    const tagList = document.getElementById('tagList');
    const searchInput = document.getElementById('searchInput');
    const modeToggle = document.getElementById('modeToggle');
    const errorMessage = document.getElementById('errorMessage');

    let selectedTags = [];

    fetch('/api/v1/articles')
        .then(response => response.json())
        .then(data => {
            posts = data;
            uniqueTags = [...new Set(posts.map(post => post.tag))];
            renderTags(uniqueTags);
            renderPosts(posts);
        })
        .catch(error => {
            console.error('글 목록을 가져오는 중 오류 발생:', error);
            showErrorMessage('글 목록을 가져오는 중 오류가 발생했습니다.');
        });

    const savedMode = localStorage.getItem('mode');
    if (savedMode === 'dark') {
        document.body.classList.add('dark-mode');
        modeToggle.checked = true;
    }

    function renderTags(tags) {
        tagList.innerHTML = '';
        tags.forEach(tag => {
            const li = document.createElement('li');
            const button = document.createElement('button');
            button.textContent = tag;
            button.className = 'tag-button';
            button.addEventListener('click', () => toggleTagSelection(button, tag));
            li.appendChild(button);
            tagList.appendChild(li);
        });
    }

    function toggleTagSelection(button, tag) {
        if (selectedTags.includes(tag)) {
            selectedTags = selectedTags.filter(t => t !== tag);
            button.classList.remove('active');
        } else {
            selectedTags.push(tag);
            button.classList.add('active');
        }
        filterPosts();
    }

    function renderPosts(filteredPosts) {
        postList.innerHTML = '';
        errorMessage.style.display = 'none';

        if (filteredPosts.length === 0) {
            showErrorMessage('조건에 맞는 글이 없습니다.');
            return;
        }

        filteredPosts.forEach(post => {
            const div = document.createElement('div');
            div.className = 'post-item';
            div.textContent = post.title;
            div.addEventListener('click', () => openPost(post.id));
            postList.appendChild(div);
        });
    }

    function showErrorMessage(message) {
        postList.innerHTML = '';
        errorMessage.textContent = message;
        errorMessage.style.display = 'block';
    }

    function filterPosts() {
        const query = searchInput.value.toLowerCase();
        let filtered = posts;
        if (query) {
            filtered = filtered.filter(post => post.title.toLowerCase().includes(query));
        }
        if (selectedTags.length > 0) {
            filtered = filtered.filter(post => selectedTags.includes(post.tag));
        }
        renderPosts(filtered);
    }

    searchInput.addEventListener('input', filterPosts);

    function openPost(id) {
        window.location.href = `/post.html?id=${id}`;
    }

    modeToggle.addEventListener('change', () => {
        if (modeToggle.checked) {
            document.body.classList.add('dark-mode');
            localStorage.setItem('mode', 'dark');
        } else {
            document.body.classList.remove('dark-mode');
            localStorage.setItem('mode', 'light');
        }
    });
});