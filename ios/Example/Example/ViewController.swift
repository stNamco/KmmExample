//
//  ViewController.swift
//  Example
//
//  Created by stnamco on 2020/02/08.
//  Copyright © 2020 namco. All rights reserved.
//

import UIKit
import SharedCode

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()

        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        label.center = view.center
        label.textAlignment = .center
        label.font = label.font.withSize(25)
        label.text = Greeting().greeting()
        view.addSubview(label)
    }
}

