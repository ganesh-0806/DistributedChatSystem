import React from 'react';

class Users extends React.Component {
    render() {
        return (
            <ul className="user_list">
                {
                    this.props.users.map((user) => {
                        return (
                            <li className="user_list__item" key={user.userName} value={user.userName} onClick={(e) => this.props.selectUser(e.target.value)}>
                                {user.userName}
                            </li>
                        );
                    })
                }
            </ul>
        );
    }
}

UserList.propTypes = {
    users: React.PropTypes.array.isRequired
};

export default Users;